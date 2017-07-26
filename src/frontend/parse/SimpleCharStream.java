package frontend.parse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * An implementation of interface CharStream, where the stream is assumed to
 * contain only ASCII characters (without unicode processing).
 * NOTE: This class is LEGACY and a product of the original JavaCC generation of this parser code.
 * As of 2017-07-26, it has been pruned of unused code and has maximal encapsulation.
 * Feel free to remove or refactor it if it no longer satisfies requirements.
 */
public class SimpleCharStream {
    private int bufsize;
    private int available;
    private int tokenBegin;
    /** Position in buffer. */
    private int bufpos = -1;
    private int bufline[];
    private int bufcolumn[];

    private int column = 0;
    private int line = 1;

    private boolean prevCharIsCR = false;
    private boolean prevCharIsLF = false;

    private final Reader inputStream;

    private char[] buffer;
    private int maxNextCharInd = 0;
    private int inBuf = 0;
    private final int tabSize = 4;

    /** Constructor. */
    public SimpleCharStream(Reader dstream, int startline, int startcolumn) {
        inputStream = dstream;
        line = startline;
        column = startcolumn - 1;

        available = bufsize = 4096;
        buffer = new char[bufsize];
        bufline = new int[bufsize];
        bufcolumn = new int[bufsize];
    }

    /** Start. */
    public char BeginToken() throws IOException {
        tokenBegin = -1;
        char c = readChar();
        tokenBegin = bufpos;
        return c;
    }

    /** Backup a number of characters. */
    public void backup(int amount) {
        inBuf += amount;
        if ((bufpos -= amount) < 0) bufpos += bufsize;
    }

    /** Read a character. */
    public char readChar() throws IOException {
        if (inBuf > 0) {
            --inBuf;
            if (++bufpos == bufsize) bufpos = 0;
            return buffer[bufpos];
        }

        if (++bufpos >= maxNextCharInd) FillBuff();

        char c = buffer[bufpos];

        UpdateLineColumn(c);
        return c;
    }

    /** Get token beginning column number. */
    public int getBeginColumn() {
        return bufcolumn[tokenBegin];
    }

    /** Get token beginning line number. */
    public int getBeginLine() {
        return bufline[tokenBegin];
    }

    /** Get token end line number. */
    public int getEndLine() {
        return bufline[bufpos];
    }

    /** Get token end column number. */
    public int getEndColumn() {
        return bufcolumn[bufpos];
    }

    /** Get token literal value. */
    public String GetImage() {
        if (bufpos >= tokenBegin) return new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
        else return new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
    }

    /** Get the suffix. */
    public char[] GetSuffix(int len) {
        char[] ret = new char[len];

        if ((bufpos + 1) >= len) {
            System.arraycopy(buffer, bufpos - len + 1, ret, 0, len);
        } else {
            System.arraycopy(buffer, bufsize - (len - bufpos - 1), ret, 0, len - bufpos - 1);
            System.arraycopy(buffer, 0, ret, len - bufpos - 1, bufpos + 1);
        }

        return ret;
    }

    private void ExpandBuff(boolean wrapAround) {
        char[] newbuffer = new char[bufsize + 2048];
        int newbufline[] = new int[bufsize + 2048];
        int newbufcolumn[] = new int[bufsize + 2048];

        try {
            if (wrapAround) {
                System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
                System.arraycopy(buffer, 0, newbuffer, bufsize - tokenBegin, bufpos);
                buffer = newbuffer;

                System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
                System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin, bufpos);
                bufline = newbufline;

                System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
                System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin, bufpos);
                bufcolumn = newbufcolumn;

                maxNextCharInd = (bufpos += (bufsize - tokenBegin));
            } else {
                System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
                buffer = newbuffer;

                System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
                bufline = newbufline;

                System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
                bufcolumn = newbufcolumn;

                maxNextCharInd = (bufpos -= tokenBegin);
            }
        } catch (Throwable t) {
            throw new Error(t.getMessage());
        }


        bufsize += 2048;
        available = bufsize;
        tokenBegin = 0;
    }

    private void FillBuff() throws IOException {
        if (maxNextCharInd == available) {
            if (available == bufsize) {
                if (tokenBegin > 2048) {
                    bufpos = maxNextCharInd = 0;
                    available = tokenBegin;
                } else if (tokenBegin < 0) {
                    bufpos = maxNextCharInd = 0;
                } else {
                    ExpandBuff(false);
                }
            } else if (available > tokenBegin) {
                available = bufsize;
            } else if ((tokenBegin - available) < 2048) {
                ExpandBuff(true);
            } else {
                available = tokenBegin;
            }
        }

        int i;
        try {
            if ((i = inputStream.read(buffer, maxNextCharInd, available - maxNextCharInd)) == -1) {
                inputStream.close();
                throw new IOException();
            } else {
                maxNextCharInd += i;
            }
            return;
        } catch (IOException e) {
            --bufpos;
            backup(0);
            if (tokenBegin == -1) tokenBegin = bufpos;
            throw e;
        }
    }

    private void UpdateLineColumn(char c) {
        column++;

        if (prevCharIsLF) {
            prevCharIsLF = false;
            line += (column = 1);
        } else if (prevCharIsCR) {
            prevCharIsCR = false;
            if (c == '\n') prevCharIsLF = true;
            else line += (column = 1);
        }

        switch (c) {
            case '\r' :
                prevCharIsCR = true;
                break;
            case '\n' :
                prevCharIsLF = true;
                break;
            case '\t' :
                column--;
                column += (tabSize - (column % tabSize));
                break;
            default :
                break;
        }

        bufline[bufpos] = line;
        bufcolumn[bufpos] = column;
    }
}
