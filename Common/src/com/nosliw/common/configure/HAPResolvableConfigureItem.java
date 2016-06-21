package com.nosliw.common.configure;

/*
 * wrapper for string content that need to be interpolated
 */
abstract class HAPResolvableConfigureItem extends HAPConfigureItem{
	
	//whether the item is resolved
	private boolean m_resolved = false;
	
	//raw content: the content before resolved
	private String m_rawString;
	
	//resolved content: the content after resolved
	private String m_resolvedString;
	
	public HAPResolvableConfigureItem(String rawString){
		this.m_rawString = rawString;
	}
	
	public String getRawString(){ return this.m_rawString; }
	public String getResolvedString(){ return this.m_resolvedString; }
	public boolean resolved(){return this.m_resolved;}
	
	public void resolved(String content){
		if(content!=null){
			//resolved
			this.m_resolved = true;
			this.setResolvedContent(content);
		}
	}

	//the method to process resolved content
	abstract protected void setResolvedContent(String resolvedContent);
}
