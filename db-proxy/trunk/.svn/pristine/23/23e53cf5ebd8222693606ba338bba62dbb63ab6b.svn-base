package appcloud.dbproxy.util;

import java.lang.reflect.Field;

import appcloud.common.model.VmMacSequence;

public class ModelTest {

    /**
     * @Title: main
     * @Description: TODO
     * @param args
     */
    public static void main(String[] args) {
        Class<?> clazz = VmMacSequence.class;
        Field[] fields = clazz.getDeclaredFields();
        
        String className = clazz.getSimpleName();
        String flClassName = getFirstLower(className);
        String modelName = className + "Table";
        String constructor = "\tpublic "+ modelName+ "(" + className + " " + flClassName + ") {\n";
    
        String importSome = "import javax.persistence.Column;\n" + 
        "import javax.persistence.Entity;\n" +
        "import javax.persistence.GeneratedValue; \n" +
        "import javax.persistence.GenerationType; \n" + 
        "import javax.persistence.Id; \n" + 
        "import javax.persistence.Table; \n";
        StringBuilder sb = new StringBuilder(importSome);
        sb.append("@Entity\n");
        sb.append("@Table(name=\"basic_load\")\n");
        sb.append("public " + modelName + " extends "  + className + "{\n");
        sb.append(constructor);
        sb.append("\t\tsuper();\n");
        sb.append("\t}\n");
        sb.append(constructor);
//        System.out.println(fields.length);
        for (Field field : fields){
//            field.getClass().
            String fuFieldName = getFirstUpper(field.getName());
            sb.append("\t\tthis.set").append(fuFieldName).append("(" + flClassName + ".get").append(fuFieldName + "());\n");
        }
        
        sb.append("\t}\n");
        
        for (Field field : fields){
//          field.getClass().
          String fieldName = field.getName();
          String fuFieldName = getFirstUpper(fieldName);
          String getMethod = "get" + fuFieldName;
          String setMethod = "set" + fuFieldName;
          String typeName = field.getType().getSimpleName();
          
          sb.append("\t@Column(name = \"" + fieldName + "\")\n");
          sb.append("\tpublic ").append(typeName).append(" " + getMethod + "(){\n\t\treturn super.get" + getFirstUpper(fieldName) + "();\n\t}\n");
          sb.append("\tpublic void").append(" " + setMethod + "(").append(typeName + " " + fieldName + "){\n\t\tsuper.set" + getFirstUpper(fieldName) + "(" + fieldName + ");\n\t}\n");

        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    private static String getFirstLower(String className){
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
    
    private static String getFirstUpper(String className){
        return className.substring(0, 1).toUpperCase() + className.substring(1);
    }
    
    private static String getDbName(String className){
        if( className.startsWith("Vm")){
           String[] str = className.substring(2).split("[A-Z]");
          System.out.println(str);
        }
        return null;
    }
}
