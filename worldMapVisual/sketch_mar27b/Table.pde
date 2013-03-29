class Table {
  int rowCount;
  String[] ips;
  float[][] positions;
  long[] times;
  long min_time;
  long max_time;
  long interval;
  Table(String fileName) {
    String[] rows = loadStrings(fileName);
    rowCount = rows.length;
    ips = new String[rowCount];
    positions = new float[rowCount][2];
    times = new long[rowCount];
    
    for(int i = 0; i < rowCount; i++) {
      String[] pieces = split(rows[i],',');
      ips[i] = pieces[3];
      times[i] = Long.parseLong(pieces[2]);
      positions[i][0] = parseFloat(pieces[5]);
      positions[i][1] = parseFloat(pieces[6]);
    }
    
    min_time = times[0];
    max_time = times[rowCount - 1];
    interval = (max_time - min_time)/10000;
  }
  
  int getRowCount() {
    return rowCount;
  }
  
  String getIP(int rowIndex) {
    return ips[rowIndex];
  }
  
  long getIPTime(int rowIndex) {
    return times[rowIndex];
  }
  
  float getIPLongitude(int rowIndex) {
    return positions[rowIndex][0];
  }
  
  float getIPLatitude(int rowIndex) {
    return positions[rowIndex][1];
  }
  
  long getMinTime() {
    return min_time;
  }
  
  long getMaxTime() {
    return max_time;
  }
  
  long getInterval() {
    return interval;
  }
}
