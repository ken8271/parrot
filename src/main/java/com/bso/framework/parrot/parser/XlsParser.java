package com.bso.framework.parrot.parser;

import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bso.commons.utils.DateUtils;
import com.bso.framework.parrot.handler.DefaultElementHandler;
import com.bso.framework.parrot.handler.ElementHandler;
import com.bso.framework.parrot.marshaller.ColumnMarshaller;
import com.bso.framework.parrot.marshaller.Marshaller;

public class XlsParser extends AbstractParser {
	
	private Logger _logger = LoggerFactory.getLogger(XlsParser.class);
	
	public XlsParser() {
		super(new ColumnMarshaller(), new DefaultElementHandler());
	}
	
	public XlsParser(Marshaller marshaller){
		super(marshaller, new DefaultElementHandler());
	}
	
	public XlsParser(ElementHandler handler){
		super(new ColumnMarshaller(), handler);
	}
	
	public XlsParser(Marshaller marshaller , ElementHandler handler){
		super(marshaller, handler);
	}

	@Override
	public <T> void parse(InputStream is, Class<T> clazz, int startPos, int fetchSize) throws Exception {
		HSSFWorkbook book = new HSSFWorkbook(is);
		HSSFSheet sheet = book.getSheetAt(0);
		
		int rowCount = sheet.getPhysicalNumberOfRows();
		
		_logger.info("[parse] - begin to parse excel file.");
		
		handler.onStart();
		
		for(int i=startPos ; i<rowCount && i<startPos + fetchSize ; i++){
			parseElement(sheet.getRow(i), clazz);
		}
		
		handler.onEnd();
	}
	
	private <T> void parseElement(HSSFRow row , Class<T> clazz) throws Exception {
		boolean isBlank = true;
		try {
			handler.onElementStart(null);
			
			int colCount = row.getPhysicalNumberOfCells();

			String[] props = new String[colCount];
			for(int j=0 ; j<colCount ; j++) {
				String v = getCellValue(row.getCell(j));
				
				if(StringUtils.isNotBlank(v)){
					props[j] = v;
					isBlank = false;
				}
			}			
			
			if(!isBlank){
				handler.onElementEnd(marshaller.unmarshal(clazz, props));
			}else {
				throw new Exception("this line is an empty line");
			}
			
		} catch (Exception e) {
			handler.onException(e);
		}
	}
	
	private String getCellValue(HSSFCell cell){
		if(cell == null) return "";
		
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING: return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN : return Boolean.toString(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_NUMERIC : 
			if(HSSFDateUtil.isCellDateFormatted(cell))
				return DateUtils.formatDateTime("yyyyMMdd", HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
			else
				return new BigDecimal(cell.getNumericCellValue()).toPlainString();
		case HSSFCell.CELL_TYPE_FORMULA : return "";
		case HSSFCell.CELL_TYPE_BLANK : return "";
		default:return "";
		}
	}
}
