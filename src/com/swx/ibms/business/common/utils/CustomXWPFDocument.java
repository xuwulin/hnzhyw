package com.swx.ibms.business.common.utils;


import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
  
/**
 * 自定义 XWPFDocument，并重写 createPicture()方法
 */
public class CustomXWPFDocument extends XWPFDocument {  
    public CustomXWPFDocument(InputStream in) throws IOException {  
        super(in);  
    }  
  
    public CustomXWPFDocument() {  
        super();  
    }  
  
    public CustomXWPFDocument(OPCPackage pkg) throws IOException {  
        super(pkg);  
    }  
  
    /**
     * @param id
     * @param width 宽
     * @param height 高
     * @param paragraph  段落
     */
    public void createPicture(int id, int width, int height,XWPFParagraph paragraph) {  
        final int EMU = 9525;  
        width *= EMU;  
        height *= EMU;  
        String  blipId = getAllPictures().get(id).getPackageRelationship().getId();  
        CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
        StringBuffer picXml = new StringBuffer(""); 
        picXml.append("<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">");
        picXml.append("<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">");
        picXml.append("      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">");
        picXml.append("         <pic:nvPicPr>");
        picXml.append("            <pic:cNvPr id=\""  );
        picXml.append(id  );
        picXml.append("\" name=\"Generated\"/>"  );
        picXml.append("            <pic:cNvPicPr/>"  );
        picXml.append("         </pic:nvPicPr>"  );
        picXml.append("         <pic:blipFill>"  );
        picXml.append("            <a:blip r:embed=\""  );
        picXml.append(blipId  );
        picXml.append("\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"  );
        picXml.append("            <a:stretch>"  );
        picXml.append("               <a:fillRect/>"  );
        picXml.append("            </a:stretch>"  );
        picXml.append("         </pic:blipFill>"  );
        picXml.append("         <pic:spPr>"  );
        picXml.append("            <a:xfrm>"  );
        picXml.append("               <a:off x=\"0\" y=\"0\"/>"  );
        picXml.append("               <a:ext cx=\""  );
        picXml.append(width  );
        picXml.append("\" cy=\""  );
        picXml.append(height  );
        picXml.append("\"/>"  );
        picXml.append("            </a:xfrm>"  );
        picXml.append("            <a:prstGeom prst=\"rect\">"  );
        picXml.append("               <a:avLst/>"  );
        picXml.append("            </a:prstGeom>"  );
        picXml.append("         </pic:spPr>"  );
        picXml.append("      </pic:pic>"  );
        picXml.append("   </a:graphicData>");
        picXml.append("</a:graphic>");

        inline.addNewGraphic().addNewGraphicData();  
        XmlToken xmlToken = null;  
        try {  
            xmlToken = XmlToken.Factory.parse(picXml.toString());  
        } catch (XmlException xe) {  
            xe.printStackTrace();  
        }  
        inline.set(xmlToken); 
        
        inline.setDistT(0);    
        inline.setDistB(0);    
        inline.setDistL(0);    
        inline.setDistR(0);    
        
        CTPositiveSize2D extent = inline.addNewExtent();  
        extent.setCx(width);  
        extent.setCy(height);  
        
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();    
        docPr.setId(id);   
        docPr.setName("图片" + id);    
        docPr.setDescr("测试"); 
    }  
}  
