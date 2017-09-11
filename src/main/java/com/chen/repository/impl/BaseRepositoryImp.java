package com.chen.repository.impl;

import com.chen.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 陈忠意 on 2017/8/31.
 */
public class BaseRepositoryImp<T> implements BaseRepository<T>{

    private Class<T> model;

    public BaseRepositoryImp(String tableName, Class<T> model){
        this.model = model;
        Field[] fields = model.getDeclaredFields();
        String[] names = new String[fields.length];
        int[] sqlTypes = new int[fields.length];
        for(int i = 0;i < fields.length;++i){
            names[i] = fields[i].getName();
            sqlTypes[i] = translateFromJavaTypeToSqlType(fields[i].getType().getTypeName());
        }
        this.names = names;
        this.sqlTypes = sqlTypes;

        this.ADD = "insert into " + tableName + " (";
        for(int i = 0, j = 0;i < names.length;++i){
            if(names[i].equals("id"))   continue;
            ++j;
            if(names[i].equals("right"))    this.ADD += "`" + names[i] + "`";
            else
                this.ADD += names[i];
            if(j != names.length - 1)   this.ADD += ", ";
        }
        this.ADD += ") values(";
        for(int i = 0, j = 0;i < names.length;++i){
            if(names[i].equals("id"))   continue;
            ++j;
            this.ADD += "?";
            if(j != names.length - 1)   this.ADD += ", ";
        }
        this.ADD += ")";

        this.UPDATE = "update " + tableName + " set ";
        for(int i = 0, j = 0;i < names.length;++i){
            if(names[i].equals("id"))   continue;
            ++j;
            if(names[i].equals("right"))    this.UPDATE += "`right` = ?";
            else
                this.UPDATE += names[i] + " = ?";
            if(j != names.length - 1)   this.UPDATE += ", ";
        }
        this.UPDATE += " where id = ?";

        this.DELETE = "delete from " + tableName + " where id = ?";

        this.FIND_ONE = "select * from " + tableName + " where id = ?";

        this.FIND_ALL = "select * from " + tableName;
    }

    public static int translateFromJavaTypeToSqlType(String javaType){
        if(javaType.equals("java.lang.String")) return Types.VARCHAR;
        else if(javaType.equals("long"))  return Types.BIGINT;
        else if(javaType.equals("int"))   return Types.BIGINT;
        else if(javaType.equals("java.sql.Date"))   return Types.DATE;
        return 0;
    }

    @Autowired
    public JdbcTemplate template;

    private String[] names;
    private int[] sqlTypes;

    private String ADD;
    public long add(T modelObject){
        System.out.println(modelObject);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con ->{
            PreparedStatement ps = con.prepareStatement(this.ADD, Statement.RETURN_GENERATED_KEYS);
            for(int i = 0, j = 1;i < this.names.length;++i){
                if(names[i].equals("id"))   continue;
                String methodName = "get" + this.names[i].substring(0, 1).toUpperCase() + this.names[i].substring(1, names[i].length());
                try {
                    Method method = this.model.getMethod(methodName);
                    System.out.println("method: " + methodName);
                    System.out.println("value: " + method.invoke(modelObject));
                    ps.setObject(j++, method.invoke(modelObject), this.sqlTypes[i]);
                }catch(NoSuchMethodException e){
                    e.printStackTrace();
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }catch(InvocationTargetException e){
                    e.printStackTrace();
                }
            }
            return ps;
        };
        template.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private String DELETE;
    public void delete(long id){
        template.update(this.DELETE, id);
    }

    private String UPDATE;
    public void update(T modelObject){
        template.update(this.UPDATE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                for(int i = 0, j = 1;i < names.length;++i){
                    if(names[i].equals("id"))   continue;
                    String methodName = "get" + names[i].substring(0, 1).toUpperCase() + names[i].substring(1, names[i].length());
                    try {
                        Method method = model.getMethod(methodName);
                        System.out.println("method: " + methodName);
                        System.out.println("value: " + method.invoke(modelObject));
                        preparedStatement.setObject(j++, method.invoke(modelObject), sqlTypes[i]);
                    }catch(NoSuchMethodException e){
                        e.printStackTrace();
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }catch(InvocationTargetException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String FIND_ONE;
    public T findOne(long id){
        System.out.println(this.model.getClasses());
        //List<T> models = template.query(this.FIND_ONE,
        //        )
        T a = null;
        return a;
    }

    private String FIND_ALL;
    public List<T> findAll(){
        return new LinkedList<T>();
    }
}
