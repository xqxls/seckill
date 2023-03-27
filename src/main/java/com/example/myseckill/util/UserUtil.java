package com.example.myseckill.util;

import com.example.myseckill.common.CommonResult;
import com.example.myseckill.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/27 21:37
 */
@Slf4j
public class UserUtil {

    final static int count = 1000;

    public static void main(String[] args) throws Exception{

        createUser();
    }

    private static void createUser() throws Exception {
        List<User> userList = new ArrayList<>();
        for(int i=0;i<count;i++){
            User user = new User();
            user.setId(1300000000L+i);
            user.setNickname("user"+i);
            user.setSlat("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123456","1a2b3c4d"));
            user.setRegisterDate(new Date());
            user.setLoginCount(0);
            userList.add(user);
        }
        log.info("create user");
        // 插入数据库
//        Connection conn = getConn();
//        String sql = "insert into user(login_count,nickname,register_date,slat,password,id) values(?,?,?,?,?,?)";
//        PreparedStatement statement = conn.prepareStatement(sql);
//        for(int i=0;i<userList.size();i++){
//            User user = userList.get(i);
//            statement.setInt(1,user.getLoginCount());
//            statement.setString(2,user.getNickname());
//            statement.setTimestamp(3,  new Timestamp(user.getRegisterDate().getTime()));
//            statement.setString(4,user.getSlat());
//            statement.setString(5,user.getPassword());
//            statement.setLong(6,user.getId());
//            statement.addBatch();
//        }
//        statement.executeBatch();
//        statement.clearParameters();
//        conn.close();
//        log.info("insert to db");
        // 登录，生成userTicket
        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("C:\\Users\\86183\\Desktop\\config.txt");
        if(file.exists()){
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        raf.seek(0);
        for(int i=0;i<userList.size();i++){
            User user = userList.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile="+user.getId()+"&password="+MD5Util.inputPassToFormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=inputStream.read(buff))>=0){
                bout.write(buff,0,len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            CommonResult commonResult = mapper.readValue(response,CommonResult.class);
            String userTicket =(String)commonResult.getObj();
            log.info("create userTicket:"+user.getId());
            String row = user.getId()+","+userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            log.info("write to file:"+user.getId());
        }
        raf.close();
        log.info("over……");
    }

    private static Connection getConn() throws Exception{
        String url= "jdbc:mysql://localhost:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        String username = "root";
        String password = "429006huzhuo";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }
}
