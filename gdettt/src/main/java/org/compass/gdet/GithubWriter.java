package org.compass.gdet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.InputMismatchException;

public class GithubWriter extends Writer {

   private Writer writeWithThis;
   
   public GithubWriter( Class writerClass ) throws Exception {
      try {
         writeWithThis = (Writer) writerClass.newInstance();
      } catch ( Exception e ) {
         throw e;
      }
   }

   public Writer append( char c ) {
      try {
         writeWithThis = writeWithThis.append(c);
      } finally {
         return this.writeWithThis;
      }
   }

   public Writer append( CharSequence csq ) {
      try {
         writeWithThis = writeWithThis.append(csq);
      } finally {
         return this.writeWithThis;
      }
   }

   public Writer append( CharSequence csq, int start, int end ) {
      try {
         writeWithThis = writeWithThis.append(csq, start, end);
      } finally {
         return this.writeWithThis;
      }
   }

   public void close() {
      try {
         writeWithThis.close();
      } catch ( Exception e ) {}
   }

   public void flush() {
      try {
         writeWithThis.close();
      } catch ( Exception e ) {}
   }

   public void write( char[] cbuf ) {
      try {
         writeWithThis.write(cbuf);
      } catch ( Exception e ) {}
   }

   public void write( char[] cbuf, int off, int len ) {
      try {
         writeWithThis.write(cbuf, off, len);
      } catch ( Exception e ) {}
   }

   public void write( int c ) {
      try {
         writeWithThis.write(c);
      } catch ( Exception e ) {}
   }

   public void write( String str ) {
      try {
         writeWithThis.write(str);
      } catch ( Exception e ) {}
   }

   public void write( String str, int off, int len ) {
      try {
         writeWithThis.write(str, off, len);
      } catch ( Exception e ) {}
   }

}
