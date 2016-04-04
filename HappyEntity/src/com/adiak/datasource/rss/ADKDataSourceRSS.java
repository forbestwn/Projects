package com.adiak.datasource.rss;

import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedHeader;
import it.sauronsoftware.feed4j.bean.FeedItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.simple.text.HAPText;
import com.nosliw.application.core.data.simple.text.HAPTextData;
import com.nosliw.data.exchange.HAPMap;
import com.nosliw.data.exchange.HAPMapData;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.definition.HAPEntityDefinitionBasic;
import com.nosliw.entity.operation.HAPEntityOperationFactory;
import com.nosliw.entity.operation.HAPEntityOperationInfo;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class ADKDataSourceRSS extends HAPEntityData{

	public ADKDataSourceRSS(HAPDataType dataType, HAPEntityDefinitionBasic entityInfo){
		super(dataType, entityInfo);
	}
	
	@Override
	public List<HAPEntityOperationInfo> init(Map<String, String> parms){
		List<HAPEntityOperationInfo> out = super.init(parms);
		
		HAPEntityOperationInfo op1 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.type", "map");
		out.add(op1);

		HAPEntityOperationInfo op12 = HAPEntityOperationFactory.createContainerElementNewOperation(this.getWraper().getID(), "outtype.childtypes", "1", true);
		out.add(op12);
		HAPEntityOperationInfo op13 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.1.name", "title");
		out.add(op13);
		HAPEntityOperationInfo op14 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.1.datatypeinfo.datatype.type", "atom");
		out.add(op14);
		HAPEntityOperationInfo op15 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.1.datatypeinfo.datatype.atomtype.categary", "simple");
		out.add(op15);
		HAPEntityOperationInfo op16 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.1.datatypeinfo.datatype.atomtype.type", "text");
		out.add(op16);
		
		HAPEntityOperationInfo op22 = HAPEntityOperationFactory.createContainerElementNewOperation(this.getWraper().getID(), "outtype.childtypes", "2", true);
		out.add(op22);
		HAPEntityOperationInfo op23 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.2.name", "title");
		out.add(op23);
		HAPEntityOperationInfo op24 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.2.datatypeinfo.datatype.type", "atom");
		out.add(op24);
		HAPEntityOperationInfo op25 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.2.datatypeinfo.datatype.atomtype.categary", "simple");
		out.add(op25);
		HAPEntityOperationInfo op26 = HAPEntityOperationFactory.createAttributeAtomSetOperationByString(this.getWraper().getID(), "outtype.childtypes.2.datatypeinfo.datatype.atomtype.type", "text");
		out.add(op26);

		
		return out;
	}
	
	public HAPData process(HAPData data){
		
		
		
		return null;
	}

/*
	public static void main(String[] args) {
        boolean ok = false;
        if (args.length==1) {
            try {
                URL feedUrl = new URL(args[0]);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                System.out.println(feed);

                ok = true;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }
        }

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }
	*/
	
	public static void main(String[] args) throws Exception {
		
		URL url = new URL("http://www.cbc.ca/cmlink/rss-topstories");
		
		Feed feed = FeedParser.parse(url);
		
		System.out.println("** HEADER **");
		FeedHeader header = feed.getHeader();
		System.out.println("Title: " + header.getTitle());
		System.out.println("Link: " + header.getLink());
		System.out.println("Description: " + header.getDescription());
		System.out.println("Language: " + header.getLanguage());
		System.out.println("PubDate: " + header.getPubDate());
		
		System.out.println("** ITEMS **");
		int items = feed.getItemCount();
		for (int i = 0; i < items; i++) {
			FeedItem item = feed.getItem(i);
			System.out.println("Title: " + item.getTitle());
			System.out.println("Link: " + item.getLink());
			System.out.println("Plain text description: " + item.getDescriptionAsText());
			System.out.println("HTML description: " + item.getDescriptionAsHTML());
			System.out.println("PubDate: " + item.getPubDate());
		}
		
	}	
	
}
