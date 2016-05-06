package com.kapok.schoolcar;


import java.util.Iterator;
import java.util.TreeSet;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;


public class SearchResPage extends ActionBarActivity {
    public static String type;
    private String value;
    private String value_1;
    private String value_2;
    private String value_3;
    public static ExpandableListView expandableListView;
    public static BaseExpandListViewAdapter baseExpandListViewAdapter;
    private String [] StringTBL=new String [] {"X","Q","B","Z","H","R"};
    public static MyDataBase myDataBase;
    private String[] groupStrings;
    private String[][] subStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        SetActivityBackground.set(this);
        Intent intent= getIntent();
        type=intent.getStringExtra("type");
        value_1=intent.getStringExtra("value_1");
        value_2=intent.getStringExtra("value_2");
        value_3=intent.getStringExtra("value_3");

        myDataBase.resultTable=new String("");
        value=value_1+value_2+value_3;
        myDataBase.getCarTable(value);
        for(int i=0;i<6;i++){
            value="";
            value=value_1+value_2+value_3+StringTBL[i];
            myDataBase.getCarTable(value);
        }
        for(int i=0;i<6;i++){
            value="";
            value=value_1+StringTBL[i]+value_2+value_3;
            myDataBase.getCarTable(value);
        }
        for(int i=0;i<6;i++){
            value="";
            value=value_1+value_2+StringTBL[i]+value_3;
            myDataBase.getCarTable(value);
        }
        myDataBase.getMyData();
        myDataBase.getMyRingTBL();
        myDataBase.getMyAskTBL();
        Pattern quoteRegex = Pattern.compile("[/]+");
        String [] resulttemp=quoteRegex.split(myDataBase.resultTable);
        TreeSet<ExpandListItemComp> set=new TreeSet<ExpandListItemComp>();
        for (int cnt=0; cnt < resulttemp.length; cnt++){
            set.add(new ExpandListItemComp(resulttemp[cnt]));
        }

        int index=0;
        Iterator<ExpandListItemComp> it = set.iterator();
        String [] result = new String[set.size()];
        while(it.hasNext()){
            result[index++]=it.next().getString();
        }
        subStrings=new String[result.length-1][];
        groupStrings=new String[result.length-1];
    	/*String [] tempString=new String[result.length];
    	int cnt = 0;
    	int k;
    	tempString[0] = result[cnt++];
    	for (; cnt < tempString.length-1; cnt++){
    		for ( k = 0; k < cnt; k++){
    			if (result[cnt].substring(result.length-6, result.length-1) .compareTo(tempString[k].substring(result.length-6, result.length-1) )>0){
    				continue;
    			}
    			else{
    				for (int j = cnt; j > k; j--){
    					tempString[j] = result[j-1];;
    				}				
    				break;
    			}
    		}
    		tempString[k] = result[cnt];
    	}*/

        int j=0;
        for (int i=0; i<result.length; i++) {
            Pattern pattern = Pattern.compile("[**]+");
            String [] subresult=pattern.split(result[i]);
            if(subresult.length<5){
                j=j+1;
                continue;
            }
            groupStrings[i-j]=subresult[subresult.length-1];
            subStrings[i-j]=new String[subresult.length-1];
            subStrings[i-j][0]="出发地："+subresult[1];
            subStrings[i-j][1]="途径："+subresult[2];
            subStrings[i-j][2]="终点："+subresult[3];
            subStrings[i-j][3]=subresult[4];
        }

        if(subStrings.length<1){
            ToastHelper.makeText(getApplicationContext(), "对不起，没有查找到符合要求的车次信息", Toast.LENGTH_LONG);

        }else{
            expandableListView = (ExpandableListView) findViewById(R.id.expandlistview_list);
            baseExpandListViewAdapter=new BaseExpandListViewAdapter(SearchResPage.this, groupStrings, subStrings);
            expandableListView.setAdapter(baseExpandListViewAdapter);
        }/*else{
    	}
	    	int sort_i,sort_j,sort_k;
	    	
	    	for(sort_i=0;sort_i<groupStrings.length-2;sort_i++){
	    		for(sort_j=sort_i+1;sort_j<groupStrings.length-1;sort_j++)          //每趟比较n-sort_i次  
	                if(groupStrings[sort_j-1].compareTo(groupStrings[sort_j]) > 0)          //依次比较两个相邻的数，将小数放在前面，大数放在后面  
	                {  
	                    String t=groupStrings[sort_j];  
	                    groupStrings[sort_j]=groupStrings[sort_j+1];  
	                    groupStrings[sort_j+1]=t; 
	                    for(sort_k=0;sort_k<3;sort_k++){
	                    	String p=subStrings[sort_j][sort_k];  
	                    	subStrings[sort_j][sort_k]=subStrings[sort_j+1][sort_k];  
	                    	subStrings[sort_j+1][sort_k]=p; 
	                    	
	                    }
	                }
	    	}
    	}*/
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SetActivityBackground.set(this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                SearchResPage.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }
}
