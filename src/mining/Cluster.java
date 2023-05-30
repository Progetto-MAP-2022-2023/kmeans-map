package mining;

import data.Data;
import data.Tuple;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

class Cluster implements Serializable {
	private Tuple centroid;

	private Set<Integer> clusteredData;
	
	/*mining.Cluster(){
		
	}*/

	Cluster(Tuple centroid){
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}
		
	Tuple getCentroid(){
		return centroid;
	}
	
	void computeCentroid(Data data){
		for(int i = 0; i<centroid.getLength(); i++){
			centroid.get(i).update(data,clusteredData);
		}
		
	}
	//return true if the tuple is changing cluster
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione � clusterizzata nell'array corrente
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	//remove the tuplethat has changed the cluster
	void removeTuple(int id){
		clusteredData.remove(id);
	}

	@Override
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+= centroid.get(i) + " ";
		str+=")";
		return str;

	}
	

	
	String toString(Data data){

		String str = "Centroid=(";
		for(int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")\nExamples:\n";

		int[] intArray = Arrays.stream(clusteredData.toArray()).mapToInt( x-> (int) x).toArray();

		for(int i = 0 ; i < intArray.length; i++){
			str += "[" ;
			for(int j = 0; j < data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(intArray[i], j) + " ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(intArray[i])) + "\n";
			
		}
		str += "AvgDistance=" + getCentroid().avgDistance(data, intArray) + "\n";

		return str;
	}

}
