package org.compass.gdet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.Constructor;

import java.util.InputMismatchException;

public class GDETOutputHandler extends Writer {

  private Writer w;

  public GDETOutputHandler( Class<?> writerClass ) throws Exception {
    try {
      Constructor<?> writerConstructor;
      if ( writerClass == OutputStreamWriter.class ) {
        writerConstructor = writerClass.getConstructor( OutputStream.class );
        w = (OutputStreamWriter) writerConstructor.newInstance( System.out );
      } else {
        throw new Exception();
      }
    } catch ( Exception e ) {
      throw new InputMismatchException( "Unsupported or Invalid Writer Class: " +
          writerClass.getTypeName() + "\nDetails:\n" + e.getStackTrace() );
    }
  }

  public Writer append( char c ) {
    try {
      w = w.append(c);
    } finally {
      return this.w;
    }
  }

  public Writer append( CharSequence csq ) {
    try {
      w = w.append(csq);
    } finally {
      return this.w;
    }
  }

  public Writer append( CharSequence csq, int start, int end ) {
    try {
      w = w.append(csq, start, end);
    } finally {
      return this.w;
    }
  }

  public void close() {
    try {
      w.close();
    } catch ( Exception e ) {}
  }

  public void flush() {
    try {
      w.close();
    } catch ( Exception e ) {}
  }

  public void write( char[] cbuf ) {
    try {
      w.write(cbuf);
    } catch ( Exception e ) {}
  }

  public void write( char[] cbuf, int off, int len ) {
    try {
      w.write(cbuf, off, len);
    } catch ( Exception e ) {}
  }

  public void write( int c ) {
    try {
      w.write(c);
    } catch ( Exception e ) {}
  }

  public void write( String str ) {
    try {
      w.write(str);
    } catch ( Exception e ) {}
  }

  public void write( String str, int off, int len ) {
    try {
      w.write(str, off, len);
    } catch ( Exception e ) {}
  }

}
