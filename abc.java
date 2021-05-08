
/**
 * Write a description of abc here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class abc {
    public float cgRatio(String dna){
        int cursor = 0;
        int count = 0;
        while((cursor + 1) <= dna.length()){
            String tempStr = dna.substring(cursor,cursor+1);
            if(tempStr.equals("C")){
            count++;
            cursor++;
            }
            else{
            cursor++;
            }
        }
        int cursor1 = 0;
        int count1 = 0;
        while((cursor1 + 1) <= dna.length()){
            String tempStr1 = dna.substring(cursor1,cursor1+1);
            if(tempStr1.equals("G")){
            count1++;
            cursor1++;
            }
            else{
            cursor1++;
            }
        }
        return (float)count/(float)count1;
    }
    
    // public void test(){
        // float a = cgRatio("ACCGGG");
        // System.out.println(a);
    // }
    
    public void processGenes(StorageResource sr){
        int count = 0; // 记录所有大于60个的字符串数量
        int count1 = 0; //记录所有cg比例大于0.35的数量
        int oldLength = 0;
        for (String item:sr.data()){
            if (item.length() > 60){
                System.out.println("这段字符长于60 "+item);
                count++;
            }
            if (cgRatio(item)>0.35){
                System.out.println("这段字符CG比大于0.35 "+ item);
                count1++;
            }
            if (item.length()>oldLength){
                oldLength = item.length();
            }
        }
        System.out.println("the number of" + 
                "string longer than 60 is " + count);
        System.out.println("the number of" + 
                "cg ratio larger than 0.35 is " + count1);
        System.out.println("The longest length is " +oldLength);     
    }
    
    
    
    
    
    
    
    public int findStopCodon(String dnaStr, int startIndex,
        String stopCodon){
        int currIndex = dnaStr.indexOf(stopCodon, startIndex+3);
        while (currIndex != -1){
            int diff = currIndex - startIndex;
            if (diff%3 == 0){
                return currIndex;
            }
            else {
                currIndex = dnaStr.indexOf(stopCodon, currIndex+1);
            }
        }
        if (currIndex == -1){  // 这个很重要，一开始没写这个，
            //导致了错误：不能处置只有起始子没有终止子的问题
            return -1;
        }
        else{
            return dnaStr.length();
        }
    }
    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG",where);
        if(startIndex == -1){
            return "";
        }
        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");
        // if (taaIndex == -1 ||tagIndex == -1||tgaIndex == -1){
            // return "";
        // }
        int minIndex = 0;
        if(taaIndex == -1 || 
        (tgaIndex != -1 && tgaIndex < taaIndex)){
            minIndex = tgaIndex;
        }
        else{
            minIndex = taaIndex;
        }
        if(minIndex == -1||
            (tagIndex != -1 && tagIndex < minIndex)){
            minIndex = tagIndex;    
        }
        if(minIndex == -1){
            return "";
        }
        // System.out.println("startIndex  "+startIndex);
        // System.out.println("minIndex + 3  "+(minIndex + 3));
        return dna.substring(startIndex, minIndex + 3);
    }
    
    public StorageResource getAllGenes(String dna){
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        while(true){
            String currentGene = findGene(dna, startIndex);
            if(currentGene.isEmpty()){
                break;
            }
            geneList.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex)+
            currentGene.length();
        }
        return geneList;
    }
    
    // public void testOn (String dna){
        // //StorageResource sr = new StorageResource();
        // System.out.println("Testing getAllGenes on "+dna);
        // StorageResource genes = getAllGenes(dna);
        // for (String g:genes.data()){
            // System.out.println(g);
        // }
        // //return getAllGenes(dna);
    // } 按照老师的方法是要用testOn的，但是我把打开StorageResource的过程内置到void里面去了。
    //所以不需要
    
    public void test(){
        FileResource fr = new FileResource("brca1line.fa"); 
        //这一步必须先把fa文件内置放到1122的文件夹中
        String dna = fr.asString().toUpperCase();
        StorageResource ssr = getAllGenes(dna);
        processGenes(ssr);
        
        
        
        //testOn("ATGATCTAATTTATGCTGCAA");ATG ATC TAA TTT ATG CTG CAA
        // ATG CTG CAA
        
        // FileResource fr = new FileResource("brca1line.fa");
        // String dna = fr.asString().toUpperCase();
        // System.out.println("dna  " + dna);
        // StorageResource genes = getAllGenes(dna);
        // for (String g:genes.data()){
            // System.out.println(g);
        // }
        
        // String fuck = findGene("ATGATCTAATTTATGCTGCAA", 0);
        // System.out.println(fuck);
        
        // StorageResource genes = getAllGenes("ATGATCTAATTTATGCTGCAATAA");
        // for (String g:genes.data()){
            // System.out.println(g);
        // }
    }
   
}
