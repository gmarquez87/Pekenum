package com.msarecovery.xml;

public class XML {
	
	public XML () {
		
	}
	
	public String createEdge(String source, String target) {
		String edge="	<edge source=\"Directory Structure/"+ source +"\" target=\"Directory Structure/"+ target +"\" label=\""+source+"(Depends On)"+target+"\">\n" + 
				"		<att type=\"string\" name=\"edge.targetArrowShape\" value=\"ARROW\"/>\n" + 
				"		<att type=\"string\" name=\"edge.color\" value=\"#0000FF\"/>\n" + 
				"		<att type=\"string\" name=\"canonicalName\" value=\""+source+"(Depends On)"+target+"\"/>\n" + 
				"		<att type=\"string\" name=\"interaction\" value=\"Depends On\"/>\n" + 
				"		<att type=\"string\" name=\"dependency kind\" value=\"Call, Type\"/>\n" + 
				"	</edge>";
		return edge;
	}
	
	public String createSubEdge(String source, String target) {
		String edge="	<edge source=\"Directory Structure/"+ source +"\" target=\"Directory Structure/"+ target +"\" label=\""+source+"(Depends On)"+target+"\">\n" + 
				"		<att type=\"string\" name=\"edge.targetArrowShape\" value=\"ARROW\"/>\n" + 
				"		<att type=\"string\" name=\"edge.color\" value=\"#A4A4A4\"/>\n" + 
				"		<att type=\"string\" name=\"canonicalName\" value=\""+source+"(Depends On)"+target+"\"/>\n" + 
				"		<att type=\"string\" name=\"interaction\" value=\"Depends On\"/>\n" + 
				"		<att type=\"string\" name=\"dependency kind\" value=\"Type\"/>\n" + 
				"	</edge>";
		return edge;
	}
	
	public String createLinkEdge(String source, String target, String framework) {
		String edge="	<edge source=\"Directory Structure/"+ source +"\" target=\"Directory Structure/"+ target +"\" label=\""+source+"(Depends On)"+target+"\">\n" + 
				//"		<att type=\"string\" name=\"edge.targetArrowShape\" value=\"ARROW\"/>\n" + 
				"		<att type=\"string\" name=\"edge.color\" value=\"#F5A9A9\"/>\n" + 
				"		<att type=\"string\" name=\"canonicalName\" value=\""+source+"(Depends On)"+target+"\"/>\n" + 
				"		<att type=\"string\" name=\"interaction\" value=\"Depends On\"/>\n" + 
				"		<att type=\"string\" name=\"dependency kind\" value=\"Type\"/>\n" +
				"    		<graphics fill=\"#F5A9A9\" width=\"2.0\">\n" + 
				"      			<att name=\"EDGE_LABEL\" value=\""+framework +"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_VISIBLE\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"     			<att name=\"EDGE_TRANSPARENCY\" value=\"255\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SIZE\" value=\"6.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_FONT_SIZE\" value=\"10\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SHAPE\" value=\"NONE\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_STROKE_SELECTED_PAINT\" value=\"#FF0000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_TRANSPARENCY\" value=\"255\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TOOLTIP\" value=\"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SIZE\" value=\"6.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_UNSELECTED_PAINT\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_WIDTH\" value=\"200.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_FONT_FACE\" value=\"Dialog,plain,10\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SHAPE\" value=\"NONE\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LINE_TYPE\" value=\"SOLID\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_COLOR\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SELECTED_PAINT\" value=\"#FFFF00\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_BEND\" value=\"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_UNSELECTED_PAINT\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_CURVED\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SELECTED\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SELECTED_PAINT\" value=\"#FFFF00\" type=\"string\" cy:type=\"String\"/>\n" + 
				"    		</graphics>\n" +
				"	</edge>";
		return edge;
	}
	
	public String createLinkEdgeMethod(String source, String target, String framework) {
		String edge="	<edge source=\"Directory Structure/"+ source +"\" target=\"Directory Structure/"+ target +"\" label=\""+source+"(Depends On)"+target+"\">\n" + 
				//"		<att type=\"string\" name=\"edge.targetArrowShape\" value=\"ARROW\"/>\n" + 
				"		<att type=\"string\" name=\"edge.color\" value=\"#16A309\"/>\n" + 
				"		<att type=\"string\" name=\"canonicalName\" value=\""+source+"(Depends On)"+target+"\"/>\n" + 
				"		<att type=\"string\" name=\"interaction\" value=\"Depends On\"/>\n" + 
				"		<att type=\"string\" name=\"dependency kind\" value=\"Type\"/>\n" +
				"    		<graphics fill=\"#16A309\" width=\"2.0\">\n" + 
				"      			<att name=\"EDGE_LABEL\" value=\""+framework +"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_VISIBLE\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"     			<att name=\"EDGE_TRANSPARENCY\" value=\"255\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SIZE\" value=\"6.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_FONT_SIZE\" value=\"10\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SHAPE\" value=\"NONE\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_STROKE_SELECTED_PAINT\" value=\"#FF0000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_TRANSPARENCY\" value=\"255\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TOOLTIP\" value=\"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SIZE\" value=\"6.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_UNSELECTED_PAINT\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_WIDTH\" value=\"200.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_FONT_FACE\" value=\"Dialog,plain,10\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SHAPE\" value=\"NONE\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LINE_TYPE\" value=\"SOLID\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_COLOR\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SELECTED_PAINT\" value=\"#FFFF00\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_BEND\" value=\"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_UNSELECTED_PAINT\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_CURVED\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SELECTED\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SELECTED_PAINT\" value=\"#FFFF00\" type=\"string\" cy:type=\"String\"/>\n" + 
				"    		</graphics>\n" +
				"	</edge>";
		return edge;
	}
	
	public String createLinkFF(String source, String target) {
		String edge="	<edge source=\"Directory Structure/"+ source +"\" target=\"Directory Structure/"+ target +"\" label=\""+source+"(Depends On)"+target+"\">\n" + 
				//"		<att type=\"string\" name=\"edge.targetArrowShape\" value=\"ARROW\"/>\n" + 
				"		<att type=\"string\" name=\"edge.color\" value=\"#A9F5A9\"/>\n" + 
				"		<att type=\"string\" name=\"canonicalName\" value=\""+source+"(Depends On)"+target+"\"/>\n" + 
				"		<att type=\"string\" name=\"interaction\" value=\"Depends On\"/>\n" + 
				"		<att type=\"string\" name=\"dependency kind\" value=\"Type\"/>\n" +
				"    		<graphics fill=\"#F5A9A9\" width=\"2.0\">\n" + 
				"      			<att name=\"EDGE_LABEL\" value=\""+"" +"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_VISIBLE\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"     			<att name=\"EDGE_TRANSPARENCY\" value=\"255\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SIZE\" value=\"6.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_FONT_SIZE\" value=\"10\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SHAPE\" value=\"NONE\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_STROKE_SELECTED_PAINT\" value=\"#FF0000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_TRANSPARENCY\" value=\"255\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TOOLTIP\" value=\"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SIZE\" value=\"6.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_UNSELECTED_PAINT\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_WIDTH\" value=\"200.0\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_FONT_FACE\" value=\"Dialog,plain,10\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SHAPE\" value=\"NONE\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LINE_TYPE\" value=\"SOLID\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_LABEL_COLOR\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_SELECTED_PAINT\" value=\"#FFFF00\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_BEND\" value=\"\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_TARGET_ARROW_UNSELECTED_PAINT\" value=\"#000000\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_CURVED\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SELECTED\" value=\"true\" type=\"string\" cy:type=\"String\"/>\n" + 
				"      			<att name=\"EDGE_SOURCE_ARROW_SELECTED_PAINT\" value=\"#FFFF00\" type=\"string\" cy:type=\"String\"/>\n" + 
				"    		</graphics>\n" +
				"	</edge>";
		return edge;
	}
	
	public String createNode(String id, String resto) {
		String newline = System.getProperty("line.separator");
		String node="	<node id=\"Directory Structure/" + id + "\""+ " label=\"" + id + " id:Directory Structure/"+ id + "\">\n"+ 
				"		<att type=\"string\" name=\"node.shape\" value=\"rect\"/>\n" + 
				"		<att type=\"string\" name=\"node.fontSize\" value=\"5\"/>\n" + 
				"		<att type=\"string\" name=\"node.label\" value=\"" + id + "-->" + resto + "\"/>\n" + 
				"		<att type=\"string\" name=\"longName\" value=\"Directory Structure/" + id + "\"/>\n" +
				"		<graphics type=\"RECTANGLE\" h=\"35\" w=\"220\" x=\"0\" y=\"0\" fill=\"#A9F5F2\" width=\"3\" outline=\"#000000\" cy:nodeTransparency=\"1.0\" cy:nodeLabelFont=\"Default-0-8\" cy:borderLineType=\"solid\"/>\n" + 
				"	</node>";
		return node;
	}
	
	public String createNodeInfrastructure(String id, String resto) {
		String newline = System.getProperty("line.separator");
		String node="	<node id=\"Directory Structure/" + id + "\""+ " label=\"" + id + " id:Directory Structure/"+ id + "\">\n"+ 
				"		<att type=\"string\" name=\"node.shape\" value=\"rect\"/>\n" + 
				"		<att type=\"string\" name=\"node.fontSize\" value=\"5\"/>\n" + 
				"		<att type=\"string\" name=\"node.label\" value=\"" + id + "-->" + resto + "\"/>\n" + 
				"		<att type=\"string\" name=\"longName\" value=\"Directory Structure/" + id + "\"/>\n" +
				"		<graphics type=\"RECTANGLE\" h=\"35\" w=\"220\" x=\"0\" y=\"0\" fill=\"#F47FF2\" width=\"3\" outline=\"#000000\" cy:nodeTransparency=\"1.0\" cy:nodeLabelFont=\"Default-0-8\" cy:borderLineType=\"solid\"/>\n" + 
				"	</node>";
		return node;
	}
	
	public String createNodeUnico(String id) {
		String node="	<node id=\"Directory Structure/" + id + "\""+ " label=\"" + id + " id:Directory Structure/"+ id + "\">\n"+ 
				"		<att type=\"string\" name=\"node.shape\" value=\"rect\"/>\n" + 
				"		<att type=\"string\" name=\"node.fontSize\" value=\"5\"/>\n" + 
				"		<att type=\"string\" name=\"node.label\" value=\"" + id+ "\"/>\n" + 
				"		<att type=\"string\" name=\"longName\" value=\"Directory Structure/" + id + "\"/>\n" +
				"		<graphics type=\"RECTANGLE\" h=\"20\" w=\"100\" x=\"0\" y=\"0\" fill=\"#A9F5F2\" width=\"3\" outline=\"#000000\" cy:nodeTransparency=\"1.0\" cy:nodeLabelFont=\"Default-0-8\" cy:borderLineType=\"solid\"/>\n" + 
				"	</node>";
		return node;
	}
	
	public String createNodeUnicoInfrastructure(String id) {
		String node="	<node id=\"Directory Structure/" + id + "\""+ " label=\"" + id + " id:Directory Structure/"+ id + "\">\n"+ 
				"		<att type=\"string\" name=\"node.shape\" value=\"rect\"/>\n" + 
				"		<att type=\"string\" name=\"node.fontSize\" value=\"5\"/>\n" + 
				"		<att type=\"string\" name=\"node.label\" value=\"" + id+ "\"/>\n" + 
				"		<att type=\"string\" name=\"longName\" value=\"Directory Structure/" + id + "\"/>\n" +
				"		<graphics type=\"RECTANGLE\" h=\"20\" w=\"100\" x=\"0\" y=\"0\" fill=\"#F47FF2\" width=\"3\" outline=\"#000000\" cy:nodeTransparency=\"1.0\" cy:nodeLabelFont=\"Default-0-8\" cy:borderLineType=\"solid\"/>\n" + 
				"	</node>";
		return node;
	}
	
	public String createNodeUnicoApplication(String id) {
		String node="	<node id=\"Directory Structure/" + id + "\""+ " label=\"" + id + " id:Directory Structure/"+ id + "\">\n"+ 
				"		<att type=\"string\" name=\"node.shape\" value=\"rect\"/>\n" + 
				"		<att type=\"string\" name=\"node.fontSize\" value=\"5\"/>\n" + 
				"		<att type=\"string\" name=\"node.label\" value=\"" + id+ "\"/>\n" + 
				"		<att type=\"string\" name=\"longName\" value=\"Directory Structure/" + id + "\"/>\n" +
				"		<graphics type=\"RECTANGLE\" h=\"20\" w=\"100\" x=\"0\" y=\"0\" fill=\"#FF9B49\" width=\"3\" outline=\"#000000\" cy:nodeTransparency=\"1.0\" cy:nodeLabelFont=\"Default-0-8\" cy:borderLineType=\"solid\"/>\n" + 
				"	</node>";
		return node;
	}
	
	public String createSubNode(String id) {
		String node="	<node id=\"Directory Structure/" + id + "\""+ " label=\"" + id + " id:Directory Structure/"+ id + "\">\n"+ 
				"		<att type=\"string\" name=\"node.shape\" value=\"rect\"/>\n" + 
				"		<att type=\"string\" name=\"node.fontSize\" value=\"5\"/>\n" + 
				"		<att type=\"string\" name=\"node.label\" value=\"" + id+ "\"/>\n" + 
				"		<att type=\"string\" name=\"longName\" value=\"Directory Structure/" + id + "\"/>\n" +
				"		<graphics type=\"RECTANGLE\" h=\"10\" w=\"100\" x=\"0\" y=\"0\" fill=\"#58ACFA\" width=\"1\" outline=\"#000000\" cy:nodeTransparency=\"1.0\" cy:nodeLabelFont=\"Default-0-8\" cy:borderLineType=\"solid\"/>\n" + 
				"	</node>";
		return node;
	}
	
	public String createHeader() {
		String header="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<graph xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cy=\"http://www.cytoscape.org\" xmlns=\"http://www.cs.rpi.edu/XGMML\" label=\" Structural Microservice Architecture\" Directed=\"0\" Graphic=\"0\" Layout=\"Circular\">\n" + 
				"	<att name=\"documentVersion\" value=\"1.1\"/>\n" + 
				"	<att name=\"networkMetadata\">\n" + 
				"		<rdf:RDF>\n" + 
				"			<rdf:Description rdf:about=\"http://www.cytoscape.org/\">\n" + 
				"				<dc:type>Structural Microservice Architecture</dc:type>\n" + 
				"				<dc:description/>\n" + 
				"				<dc:identifier>-</dc:identifier>\n" + 
				"				<dc:date/>\n" + 
				"				<dc:title/>\n" + 
				"				<dc:source>http://localhost/?p=</dc:source>\n" + 
				"				<dc:format>Cytoscape-XGMML</dc:format>\n" + 
				"			</rdf:Description>\n" + 
				"		</rdf:RDF>\n" + 
				"	</att>";
		return header;
	}
	
	public String createEnd() {
		String end= "</graph>";
		return end;
	}

}
