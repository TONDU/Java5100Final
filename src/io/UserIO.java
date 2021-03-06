package io;

import java.sql.*;
import java.util.ArrayList;

import java.util.HashMap;


public class UserIO implements  UserIOInterface {

    //private Connection connection = null;

    private Connection connection;

    private HashMap<String,String> updatemap=new HashMap<>();
    private HashMap<String,String> addmap=new HashMap<>();
    public UserIO() {
        updatemap.put("vehicle","id,dealername,category,year,make,model,trim,type,price,images,specialid,discountprice");
        updatemap.put("dealer","dealername,url,location,zipcode,address");
        updatemap.put("special","id,dealername,startdate,enddate,title,description,disclaimer,value,maker,model,startyear,endyear,minPrice,maxPrice");
        addmap.put("vehicle","dealername,category,year,make,model,trim,type,price,images,specialid,discountprice");
       addmap.put("dealer","dealername,url,location,zipcode,address");
        addmap.put("special","dealername,startdate,enddate,title,description,disclaimer,value,maker,model,startyear,endyear,minPrice,maxPrice");

    }

    public Connection getConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            if(connection == null)
                connection= DriverManager.getConnection("jdbc:mysql://111.230.49.171/cloud","xiaoxu","xiaoxu");

            System.out.println("Success");
            return connection;

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return connection;

    }


    @Override

    public  void addData(String tableName,String data) {

        try {
            Connection connection=getConnection();
            PreparedStatement preparedStatement;

           preparedStatement = connection.prepareStatement("INSERT INTO cloud."+tableName+" ("+addmap.get(tableName)+")  VALUES ("+data+")");



            preparedStatement.executeUpdate();
            preparedStatement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override

    public  void deleteData(String tableName,String data) {


        PreparedStatement preparedStatement = null;
        Connection connection=getConnection();
        try {
            preparedStatement = connection.prepareStatement("DELETE from cloud."+tableName+" WHERE id="+data);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override


    public void updateData(String tableName,String id, String data) {



        try {
            Connection connection=getConnection();

            PreparedStatement preparedStatement=null;
            if(tableName.equals("dealer"))
                preparedStatement= connection.prepareStatement("DELETE from cloud."+tableName+" WHERE dealername="+"'"+id+"'");
            else
                preparedStatement = connection.prepareStatement("DELETE from cloud."+tableName+" WHERE id="+"'"+id+"'");

            preparedStatement.executeUpdate();
            preparedStatement=connection.prepareStatement("INSERT INTO cloud."+tableName+" ("+updatemap.get(tableName)+")  VALUES ("+data+")");

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override

    public ArrayList<String> getData(String sql) {

        Connection connection=getConnection();
        ResultSet resultSet=null;
        ArrayList<String> resset=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           resultSet=preparedStatement.executeQuery();
           int col=resultSet.getMetaData().getColumnCount();
           while(resultSet.next()){
               String res="";
               for(int i=1;i<=col;i++){
                   if(i==col)
                       res=res+resultSet.getString(i);
                   else
                       res=res+resultSet.getString(i)+"~";
               }
               resset.add(res);
           }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resset;
    }




    /*public void insertdealer(Dealers dealer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO demo.dealer (name ,url,location) VALUES (?, ?,?)");
            preparedStatement.setString(1,  dealer.getName());
            preparedStatement.setString(2,  dealer.getLocation());
            preparedStatement.setString(3,  dealer.getUrl());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/

    /*public void insertVehicle(Vehicle vehicle){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO demo.Vehicle (id ,dealername,category,year,make,model,trim,type,price,images) VALUES (?, ?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,  vehicle.getId());
            preparedStatement.setString(2,  vehicle.getDealername());
            preparedStatement.setString(3,  vehicle.getCategory());
            preparedStatement.setString(4,  vehicle.getYear());
            preparedStatement.setString(5,  vehicle.getMake());
            preparedStatement.setString(6,  vehicle.getModel());
            preparedStatement.setString(7,  vehicle.getTrim());
            preparedStatement.setString(8,  vehicle.getType());
            preparedStatement.setString(9,  vehicle.getPrice());
            preparedStatement.setString(10,  vehicle.getImages());


            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

}
