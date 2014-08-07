package com.example.androiddatabase;

public class StudentData {
	
		   //private variables
		    int id;
		    String name;
		    String fathername;
		    String mothername;
		     
		    // Empty constructor
		    public StudentData(){
		         
		    }
		    // constructor
		    public StudentData(int id, String name, String fathername,String mothername){
		        this.id = id;
		        this.name = name;
		        this.fathername= fathername;
		        this.mothername= mothername;
		    }
		     
		    // constructor
		    public StudentData(String name, String fathername,String mothername){
		        this.name = name;
		        this.fathername =fathername;
		        this.mothername=mothername;
		        
		    }
		    // getting ID
		    public int getID(){
		        return this.id;
		    }
		     
		    // setting id
		    public void setID(int id){
		        this.id = id;
		    }
		     
		    // getting name
		    public String getName(){
		        return this.name;
		    }
		     
		    // setting name
		    public void setName(String name){
		        this.name = name;
		    }
		     
		    // getting phone number
		    public String getFathername(){
		        return this.fathername;
		    }
		     
		    // setting phone number
		    public void setFathername(String fathername){
		        this.fathername = fathername;
		 
		    } 
		        public String getMothername(){
		        	return this.mothername;
		        }
		        public void setMothername(String mothername){
			        this.mothername = mothername;
			     
		    }
		}





