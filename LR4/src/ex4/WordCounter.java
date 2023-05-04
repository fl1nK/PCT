package ex4;/* ......................................................................................... */

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

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

    Long occurrencesCount(Document document, List<String> searchedWord) {
        long count = 0;
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (searchedWord.contains(word)) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

/* ......................................................................................... */

    class DocumentSearchTask extends RecursiveTask<List<String>> {
        private final Document document;
        private final List<String> searchedWord;
        
        DocumentSearchTask(Document document, List<String> searchedWord) {
            super();
            this.document = document;
            this.searchedWord = searchedWord;
        }
        
        @Override
        protected List<String> compute() {
            return occurrencesCount(document, searchedWord) == 0 ? null
                    : Collections.singletonList(document.documentName);
        }
    }

/* ......................................................................................... */

    class FolderSearchTask extends RecursiveTask<List<String>> {
        private final Folder folder;
        private final List<String> searchedWord;
        
        FolderSearchTask(Folder folder, List<String> searchedWord) {
            super();
            this.folder = folder;
            this.searchedWord = searchedWord;
        }
        
        @Override
        protected List<String> compute() {
            List<RecursiveTask<List<String>>> forks = new LinkedList<>();
            for (Folder subFolder : folder.getSubFolders()) {
                FolderSearchTask task = new FolderSearchTask(subFolder, searchedWord);
                forks.add(task);
                task.fork();
            }
            for (Document document : folder.getDocuments()) {
                DocumentSearchTask task = new DocumentSearchTask(document, searchedWord);
                forks.add(task);
                task.fork();
            }
            List<String> result = new ArrayList<>();
            for (RecursiveTask<List<String>> task : forks) {
                List<String> taskResult = task.join();
                if (taskResult == null) {
                    continue;
                }
                result.addAll(taskResult);
            }
            return result;
        }
    }
        
/* ......................................................................................... */
    
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(4);
    
    List<String> countOccurrencesInParallel(Folder folder, List<String> searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

/* ......................................................................................... */
    
    public static void main(String[] args) throws IOException {
        String searchWords = "elit";
        List<String> keyWords = Arrays.asList(searchWords.split("\\s"));

        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File("LR4/testFolder"));

        for (String documentName : wordCounter.countOccurrencesInParallel(folder, keyWords)){
            System.out.println(documentName);
        }
    }
}

/* ......................................................................................... */