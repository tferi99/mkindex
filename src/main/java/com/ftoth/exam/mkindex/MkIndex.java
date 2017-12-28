package com.ftoth.exam.mkindex;

import com.ftoth.exam.mkindex.utils.ComparatorHu;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class MkIndex
{
    private static Logger log = Logger.getLogger(MkIndex.class);

    private static String OUT_FILE = "index.txt";
    private static String IN_ENCODING = "8859_2";
    private static String OUT_ENCODING = "8859_2";

    private Map<String, List<Integer>> wordIndex = new TreeMap<String, List<Integer>>(new ComparatorHu());

    public static void main(String[] args)
    {
        checkArgs(args);

        MkIndex app = new MkIndex();
        String fname = args[0];
        try {
            app.procesInputFile(fname);
        } catch (IOException e) {
            System.err.println("Error during processing file: " + fname + "(" + e.getMessage() + ")");
            System.exit(2);
        }
    }

    private void procesInputFile(String fileName) throws IOException
    {
        // processing lines
        try (BufferedReader  br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), IN_ENCODING)) ) {
            int lineIdx = 1;
            for (String line; (line = br.readLine()) != null; ) {
                processLine(line, lineIdx);
                lineIdx++;
            }
        }

        // creating index file
        File fout = new File(OUT_FILE);
        FileOutputStream fos = new FileOutputStream(fout);
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, OUT_ENCODING))) {
            if (log.isDebugEnabled()) {
                log.debug("=================================");
            }

            for (String word : wordIndex.keySet()) {
                StringBuilder b = new StringBuilder();
                b.append(word + " ");
                List<Integer> lines = wordIndex.get(word);
                boolean first = true;
                int prevLine = -1;
                for (Integer line : lines) {
                    if (prevLine != line) {
                        if (!first) {
                            b.append(",");
                        }
                        b.append(line);
                        first = false;
                    }
                    prevLine = line;
                }
                if (log.isDebugEnabled()) {
                    log.debug("OUT: " + b.toString());
                }
                bw.write(b.toString());
                bw.newLine();
            }
        }
    }

    private void processLine(String line, int lineIdx)
    {
        if (log.isDebugEnabled()) {
            log.debug("line[" + lineIdx + "]: [" + line + "]");
        }
        String[] words = line.split("[ \t]+");
        if (log.isDebugEnabled()) {
            log.debug(  "words: " + words.length);
        }

        for (String word : words) {
            word = word.toLowerCase();
            addWord(word, lineIdx);
        }
    }

    private void addWord(String word, int lineIdx)
    {
        if (log.isDebugEnabled()) {
            log.debug("    word:[" + word + "]");
        }
        List<Integer> indexItem = wordIndex.get(word);
        if (indexItem == null) {
            if (log.isDebugEnabled()) {
                log.debug("Index item for word[" + word + "] not found, let's create");
            }
            indexItem = new ArrayList<Integer>();
        }
        indexItem.add(new Integer(lineIdx));
        wordIndex.put(word, indexItem);
    }

    private static void checkArgs(String[] args)
    {
        // input validation
        if (args.length != 1) {
            System.err.println("Usage: MkIndex <input file>");
            System.exit(1);
        }
    }
}
