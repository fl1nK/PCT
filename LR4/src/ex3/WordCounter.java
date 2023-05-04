package ex3;/* ......................................................................................... */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/* ......................................................................................... */

class Document {
    private final List<String> lines;
    public final String documentName;
    
    Document(List<String> lines, String documentName) {
        this.lines = lines;
        this.documentName = documentName;
    }
    
    List<String> getLines() {
        return this.lines;
    }
    
    static Document fromFile(File file) throws IOException {
        List<String> lines = new LinkedList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return new Document(lines, file.getAbsolutePath());
    }
}

/* ......................................................................................... */

class Folder {
    private final List<Folder> subFolders;
    private final List<Document> documents;
    
    Folder(List<Folder> subFolders, List<Document> documents) {
        this.subFolders = subFolders;
        this.documents = documents;
    }
    
    List<Folder> getSubFolders() {
        return this.subFolders;
    }
    
    List<Document> getDocuments() {
        return this.documents;
    }
    
    static Folder fromDirectory(File dir) throws IOException {
        List<Document> documents = new LinkedList<>();
        List<Folder> subFolders = new LinkedList<>();
        for (File entry : dir.listFiles()) {
            if (entry.isDirectory()) {
                subFolders.add(Folder.fromDirectory(entry));
            } else {
                documents.add(Document.fromFile(entry));
            }
        }
        return new Folder(subFolders, documents);
    }
}

/* ......................................................................................... */

public class WordCounter {    

/* ......................................................................................... */

    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public Set<String> getAllWords(Document document) {
        Set<String> allWords = new HashSet<>();
        for (String line : document.getLines()) {
            allWords.addAll(Arrays.asList(wordsIn(line)));
        }
        return allWords;
    }

/* ......................................................................................... */

    class DocumentSearchTask extends RecursiveTask<Set<String>> {
        private final Document document;
        
        DocumentSearchTask(Document document) {
            super();
            this.document = document;
        }
        
        @Override
        protected Set<String> compute() {
            return getAllWords(document);
        }
    }

/* ......................................................................................... */

    class FolderSearchTask extends RecursiveTask<Set<String>> {
        private final Folder folder;
        FolderSearchTask(Folder folder) {
            super();
            this.folder = folder;
        }
        
        @Override
        protected Set<String> compute() {
            Set<String> result;
            List<RecursiveTask<Set<String>>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderSearchTask task = new FolderSearchTask(subFolder);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentSearchTask task = new DocumentSearchTask(document);
                forks.add(task);
                task.fork();
            }

            if (forks.size() == 0) return new HashSet<>();

            result = forks.get(0).join();
            forks.remove(0);
            for (RecursiveTask<Set<String>> task : forks) {
                result.retainAll(task.join());
            }
            return result;
        }
    }
        
/* ......................................................................................... */
    
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(4);
    
    Set<String> countOccurrencesInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }

/* ......................................................................................... */
    
    public static void main(String[] args) throws IOException {
        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File("LR4/testFolder/testEx3"));

        System.out.println(wordCounter.countOccurrencesInParallel(folder));
    }
}

/* ......................................................................................... */