import processing.pdf.*;
PFont font;
PImage mapImage;
int width = 1440;
int height = 900;

Table locationTable;
int rowCount;

Table commentTable;
int commentCount;

Table likeTable;
int likeCount;

Table listenTable;
int listenCount;

long minTime;
long maxTime;

void setup() {
  //size(width, height,PDF,"new.pdf");
  size(width, height);
  locationTable = new Table("completedubbs.txt");
  rowCount = locationTable.getRowCount();
  commentTable = new Table("comments.txt.output");
  commentCount = commentTable.getRowCount();
  likeTable = new Table("likes.txt.output");
  likeCount = likeTable.getRowCount();
  listenTable = new Table("listens.txt.output");
  listenCount = listenTable.getRowCount();
  
  mapImage = loadImage("newdit.png");
  minTime = locationTable.getMinTime() - 100 * locationTable.getInterval();
  maxTime = locationTable.getMinTime() + 100 * locationTable.getInterval();
  
  font = loadFont("Times-Roman-16.vlw");
  textFont(font);
}

void draw() {
  background(mapImage);
  
 
  
  if(maxTime > locationTable.getMaxTime() + locationTable.getInterval()) {
    fill(0,192,0);
    noStroke();
    for(int row = 0; row < rowCount; row++) {
      float x = locationTable.getIPLongitude(row);
      float y = locationTable.getIPLatitude(row);
      int r = 4;
      ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
    }
    
    fill(0,0,192);
    noStroke();
    for(int row = 0; row < commentCount; row++) {
    
      float x = commentTable.getIPLongitude(row);
      float y = commentTable.getIPLatitude(row);
      int r = 4;
      ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
    }
    
    fill(192,0,0);
    noStroke();
    for(int row = 0; row < likeCount; row++) {
    
      float x = likeTable.getIPLongitude(row);
      float y = likeTable.getIPLatitude(row);
      int r = 4;
      ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
    }
    
    fill(0,192,192);
    noStroke();
    for(int row = 0; row < listenCount; row++) {
    
      float x = listenTable.getIPLongitude(row);
      float y = listenTable.getIPLatitude(row);
      int r = 2;
      ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
    }
    //save("final.png");
    //exit();
  } else {
    fill(0,192,0);
    noStroke();
    for(int row = 0; row < rowCount; row++) {
      long time = locationTable.getIPTime(row);
      if((time > minTime) && (time < maxTime)) {
        float x = locationTable.getIPLongitude(row);
        float y = locationTable.getIPLatitude(row);
        int r = 4;
        ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
      }
    }
    
    fill(0,0,192);
    noStroke();
    for(int row = 0; row < commentCount; row++) {
      long time = commentTable.getIPTime(row);
      if((time > minTime) && (time < maxTime)) {
        float x = commentTable.getIPLongitude(row);
        float y = commentTable.getIPLatitude(row);
        int r = 4;
        ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
      }
    }
    
    fill(192,0,0);
    noStroke();
    for(int row = 0; row < likeCount; row++) {
      long time = likeTable.getIPTime(row);
      if((time > minTime) && (time < maxTime)) {
        float x = likeTable.getIPLongitude(row);
        float y = likeTable.getIPLatitude(row);
        int r = 4;
        ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
      }
    }
    
    fill(0,192,192);
    noStroke();
    for(int row = 0; row < listenCount; row++) {
      long time = listenTable.getIPTime(row);
      if((time > minTime) && (time < maxTime)) {
        float x = listenTable.getIPLongitude(row);
        float y = listenTable.getIPLatitude(row);
        int r = 2;
        ellipse(transfromLongitude(x),transfromLatitude(y),r,r);
      }
    }
  }
  minTime += locationTable.getInterval();
  maxTime += locationTable.getInterval();
    
    
  Date date = new Date(maxTime);
  Calendar calendar = Calendar.getInstance();
  calendar.setTime(date);
  textAlign(LEFT);
  fill(0);
  String message = calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DAY_OF_MONTH);
  text(message, width/2, 50);
  
  textAlign(LEFT);
  fill(0,192,0);
  text("DUBB", 50, height - 400);
  fill(192,0,0);
  text("LIKE", 50, height - 350);
  fill(0,0,192);
  text("COMMENT",50,height - 300);
  fill(0,192,192);
  text("Listen",50,height - 250);
  
}

float transfromLatitude(float latitude) {
  return height/2 - latitude*height/180;
}

float transfromLongitude(float longitude) {
  return width/2 + longitude*width/360;
}
