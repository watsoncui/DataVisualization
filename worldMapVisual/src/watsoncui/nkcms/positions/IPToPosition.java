package watsoncui.nkcms.positions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IPToPosition {

	private String mapFilename;
	private String inputFileName;
	private String outputFileName;
	private String plusMapFileName;
	private int ipColumn;
	private Map<String, String> ipMap;
	private PrintWriter pwOutputFile;
	private PrintWriter pwPlusMapFile;
	private BufferedReader brMapFile;
	private BufferedReader brInputFile;
	private Map<String, String> ipMapPlus;
	
	public IPToPosition(String mapFilename, String inputFileName, int ipColumn) {
		this.mapFilename = mapFilename;
		this.inputFileName = inputFileName;
		this.ipColumn = ipColumn;
		this.outputFileName = inputFileName + ".output";
		this.plusMapFileName = mapFilename + ".plus";
		this.ipMap = new HashMap<String, String>();
		this.ipMapPlus = new HashMap<String, String>();
	}
	
	public IPToPosition() {
		this.ipMap = new HashMap<String, String>();
		this.ipMapPlus = new HashMap<String, String>();
	}
	
	private void importIpMap() {
		importIpMap(mapFilename);
	}
	
	public void importIpMap(String mapFilename) {
		try {
			brMapFile = new BufferedReader(new FileReader(new File(mapFilename)));
			String line = null;
			
			while(null != (line = brMapFile.readLine())) {
				String[] strs = line.split(",");		
				try {
					if(!ipMap.containsKey(strs[0])) {
					ipMap.put(strs[0], strs[1] + "," + strs[2]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println(line);
					continue;
				}
			}
			brMapFile.close();
		} catch (FileNotFoundException e) {
			System.err.println(mapFilename + "not Found!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void lookUpIp(String ip, Map<String, String> ipMap, String url) throws Exception {
		Document doc = Jsoup.connect(url + ip).get();
		List<Element> elems = doc.getElementsByTag("table").get(6).getElementsByTag("td");
		String longitude = elems.get(elems.size() - 3).html().toString();
		String latitude = elems.get(elems.size() - 1).html().toString();
		if((null != longitude) && (null != latitude) && (!longitude.isEmpty()) && (!latitude.isEmpty())) {
			if(!ipMap.containsKey(ip)) {
				ipMap.put(ip, longitude + "," + latitude);
			}
			if(!ipMapPlus.containsKey(ip)) {
				ipMapPlus.put(ip, longitude + "," + latitude);
			}
		}
	}
	
	public void mapIpToPos(String inputFileName, String outputFileName, int ipColumn, Map<String, String> ipMap, String url) {
		try {
			brInputFile = new BufferedReader(new FileReader(new File(inputFileName)));
			pwOutputFile = new PrintWriter(new FileWriter(new File(outputFileName)));
			
			String line = null;
			
			while(null != (line = brInputFile.readLine())) {
				String[] strs = line.split(",");
				String ip = strs[ipColumn];
				if(ipMap.containsKey(ip)) {
					pwOutputFile.println(line + "," + ipMap.get(ip));
				} else {
					try {
						lookUpIp(ip,ipMap,url);
						if(ipMap.containsKey(ip)) {
							pwOutputFile.println(line + "," + ipMap.get(ip));
						}
					} catch (Exception e) {
						System.out.println(ip);
					}
				}
			}
			
			brInputFile.close();
			pwOutputFile.close();
		} catch (FileNotFoundException e) {
			System.err.println(inputFileName + "not Found!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mapIpToPos(String inputFileName, String outputFileName, int ipColumn, Map<String, String> ipMap) {
		mapIpToPos(inputFileName, outputFileName, ipColumn, ipMap, "http://www.geoiptool.com/zh/?IP=");
	}
	
	private void mapIpToPos() {
		mapIpToPos(inputFileName, outputFileName, ipColumn, ipMap);
	}
	
	
	public void batchIfAllInfomationGot() {
		importIpMap();
		mapIpToPos();
		exportIpMapPlus();
	}
	
	private void exportIpMapPlus() {
		exportIpMapPlus(plusMapFileName);
	}
	
	public void exportIpMapPlus(String plusMapFileName) {
		try {
			pwPlusMapFile = new PrintWriter(new FileWriter(new File(plusMapFileName)));
			
			Set<String> keySet = ipMapPlus.keySet();
			
			if(null != keySet) {
				for(String key:keySet) {
					pwPlusMapFile.println(key + "," + ipMapPlus.get(key));
				}
			}
			
			pwPlusMapFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IPToPosition it = new IPToPosition("commonips.txt", "listens.txt", 3);
		it.batchIfAllInfomationGot();
	}


	public String getMapFilename() {
		return mapFilename;
	}


	public void setMapFilename(String mapFilename) {
		this.mapFilename = mapFilename;
	}


	public String getInputFileName() {
		return inputFileName;
	}


	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}


	public String getOutputFileName() {
		return outputFileName;
	}


	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}


	public String getPlusMapFile() {
		return plusMapFileName;
	}


	public void setPlusMapFile(String plusMapFile) {
		this.plusMapFileName = plusMapFile;
	}


	public int getIpColumn() {
		return ipColumn;
	}


	public void setIpColumn(int ipColumn) {
		this.ipColumn = ipColumn;
	}

	public String getPlusMapFileName() {
		return plusMapFileName;
	}

	public void setPlusMapFileName(String plusMapFileName) {
		this.plusMapFileName = plusMapFileName;
	}

	public Map<String, String> getIpMap() {
		return ipMap;
	}

	public void setIpMap(Map<String, String> ipMap) {
		this.ipMap = ipMap;
	}

	public Map<String, String> getIpMapPlus() {
		return ipMapPlus;
	}

	public void setIpMapPlus(Map<String, String> ipMapPlus) {
		this.ipMapPlus = ipMapPlus;
	}

}
