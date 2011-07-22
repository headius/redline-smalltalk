/*
Redline Smalltalk is licensed under the MIT License

Redline Smalltalk Copyright (c) 2010 James C. Ladd

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Please see DEVELOPER-CERTIFICATE-OF-ORIGIN if you wish to contribute a patch to Redline Smalltalk.
*/
package st.redline;

// TODO.JCL - make this classloader delegate to another so we can replace the delegate at runtime to reload all classes on fly.

public class SmalltalkClassLoader extends ClassLoader {

	public SmalltalkClassLoader(java.lang.ClassLoader classLoader) {
		super(classLoader);
		initialize();
	}

	private void initialize() {
		loadProtoObject();
	}

	private void loadProtoObject() {
		try {
			loadClass("st.redline.ProtoObject");
		} catch (ClassNotFoundException e) {
			throw new RedlineException(e);
		}
	}

	public Class findClass(String className) throws ClassNotFoundException {
		System.out.println("findClass() " + className);
		SourceFile sourceFile = findSource(className);
		if (sourceFile == null)
			return super.findClass(className);
		return classFrom(sourceFile);
	}

	private Class classFrom(SourceFile sourceFile) {
		byte[] classBytes = compile(sourceFile);
		return defineClass(null, classBytes, 0, classBytes.length);
	}

	private byte[] compile(SourceFile sourceFile) {
		return createCompiler(sourceFile).compile();
	}

	private Compiler createCompiler(SourceFile sourceFile) {
		return new Compiler(sourceFile);
	}

	private SourceFile findSource(String className) {
		return sourceFileFinder(className).findSourceFile();
	}

	private SourceFileFinder sourceFileFinder(String className) {
		return new SourceFileFinder(className);
	}
}