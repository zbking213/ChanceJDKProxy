package org.zbking;


import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Proxy {

     public static String IMPORT = "import ";
     public static String SEMICOLON = ";";
     public static String PACKAGE_NAME = "package org.zbking";
     public static String ZBKINGINVOCATIONHANDLER = "org.zbking.ZbkingInvocationHandler";
     public static String EXCEPTION = "java.lang.Exception";
     public static String METHOD = "java.lang.reflect.Method";
     public static String CLASSNAME = "public class $ProxyZbking implements ";
     public static String LEFT = "{";
     public static String RIGHT = "}";
     public static String FILED = "private ZbkingInvocationHandler z";
     public static String CONSTRUCTOR = "public $ProxyZbking(ZbkingInvocationHandler z)";


    /**
     *
     * @param targetInf 目标对象接口
     * @param z 代理逻辑
     * @return
     */
    public static Object newInstance(Class<?> targetInf, ZbkingInvocationHandler z){

        Object proxyObj = null;
        //public abstract java.lang.String com.zbking.service.UserService.uMethod() throws java.lang.Exception
        Method[] methods = targetInf.getDeclaredMethods();
        String line="\n";
        String tab ="\t";
        // userService
        String infSimpleName = targetInf.getSimpleName();
        String content = "";
        String packageContent = PACKAGE_NAME + SEMICOLON;
        String importContent = IMPORT  + targetInf.getName() + SEMICOLON + line
                + IMPORT + ZBKINGINVOCATIONHANDLER + SEMICOLON + line
                + IMPORT + EXCEPTION + SEMICOLON + line
                + IMPORT + METHOD + SEMICOLON + line;
        String clazzFirstLineContent = CLASSNAME + infSimpleName + LEFT + line;
        String filedContent = tab + FILED + SEMICOLON + line;
        String constructorContent = tab + CONSTRUCTOR + LEFT + line
                + tab + tab + "this.z = z;"
                + line + tab + RIGHT + line;
        String methodContent = "";
        for (Method method : methods) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName =method.getName();
            //String.class String.class
            Class<?>[] args = method.getParameterTypes();
            String argsContent = "";
            String paramsContent = "";
            int flag = 0;
            for (Class<?> arg : args) {
                // String
                String temp = arg.getSimpleName();
                //String p0,String p1
                argsContent += temp + " p" + flag + ",";
                //p0,p1
                paramsContent += "p" + flag + ",";
                flag ++;
            }
            if (argsContent.length()>0){
                argsContent = argsContent.substring(0,argsContent.lastIndexOf(","));
                paramsContent = paramsContent.substring(0,paramsContent.lastIndexOf(","));
            }
            methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ") throws Exception " + LEFT + line
                    + tab + tab + "Method method = Class.forName(\"" + targetInf.getName() + "\").getDeclaredMethod(\"" + methodName + "\");" + line
                    +tab + tab + "return (" + returnTypeName + ")z.zbkingInvoke(method);" + line;
            methodContent += tab + RIGHT + line;

        }
        content = packageContent + importContent + clazzFirstLineContent + filedContent + constructorContent + methodContent + RIGHT;

        File file  = new File("e:\\org\\zbking\\$ProxyZbking.java");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();


            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);

            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();

            URL[] urls = new URL[]{new URL("file:e:\\\\")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("org.zbking.$ProxyZbking");

            Constructor constructor = clazz.getConstructor(ZbkingInvocationHandler.class);
            proxyObj = constructor.newInstance(z);
            //clazz.newInstance();
            //Class.forName()
        }catch (Exception e){
            e.printStackTrace();
        }
        return proxyObj;
    }
}
