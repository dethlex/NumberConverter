package com.dethlex.numberconverter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertActionTest {
	ConvertAction caDEC = new ConvertAction(ConvertType.DEC);
	ConvertAction caHEX = new ConvertAction(ConvertType.HEX);
	ConvertAction caOCT = new ConvertAction(ConvertType.OCT);
	ConvertAction caBIN = new ConvertAction(ConvertType.BIN);

	// To DEC
	@Test
	@DisplayName("DEC to DEC")
	void testFromDECtoDEC() {
		assertEquals("100", caDEC.ConvertNumber("100"),"DEC != DEC");
	}

	@Test
	@DisplayName("HEX to DEC")
	void testFromHEXtoDEC() {
		assertEquals("100", caDEC.ConvertNumber("0x64"),"DEC != HEX");
	}

	@Test
	@DisplayName("OCT to DEC")
	void testFromOCTtoDEC() {
		assertEquals("100", caDEC.ConvertNumber("0144"),"DEC != OCT");
	}

	@Test
	@DisplayName("BIN to DEC")
	void testFromBINtoDEC() {
		assertEquals("100", caDEC.ConvertNumber("0b1100100"),"DEC != BIN");
	}

	@Test
	@DisplayName("Engineering type to DEC")
	void testFromENGtoDEC() {
		assertEquals("100", caDEC.ConvertNumber("1e2"),"Engineering DEC != DEC");
	}

	// To HEX
	@Test
	@DisplayName("DEC to HEX")
	void testFromDECtoHEX() {
		assertEquals("0x64", caHEX.ConvertNumber("100"),"HEX != DEC");
	}

	@Test
	@DisplayName("HEX to HEX")
	void testFromHEXtoHEX() {
		assertEquals("0x64", caHEX.ConvertNumber("0x64"),"HEX != HEX");
	}

	@Test
	@DisplayName("OCT to HEX")
	void testFromOCTtoHEX() {
		assertEquals("0x64", caHEX.ConvertNumber("0144"),"HEX != OCT");
	}

	@Test
	@DisplayName("BIN to HEX")
	void testFromBINtoHEX() {
		assertEquals("0x64", caHEX.ConvertNumber("0b1100100"),"HEX != BIN");
	}

	@Test
	@DisplayName("Engineering type to HEX")
	void testFromENGtoHEX() {
		assertEquals("0x64", caHEX.ConvertNumber("1e2"),"Engineering DEC != HEX");
	}

	// To OCT
	@Test
	@DisplayName("DEC to OCT")
	void testFromDECtoOCT() {
		assertEquals("0144", caOCT.ConvertNumber("100"),"OCT != DEC");
	}

	@Test
	@DisplayName("HEX to OCT")
	void testFromHEXtoOCT() {
		assertEquals("0144", caOCT.ConvertNumber("0x64"),"OCT != HEX");
	}

	@Test
	@DisplayName("OCT to OCT")
	void testFromOCTtoOCT() {
		assertEquals("0144", caOCT.ConvertNumber("0144"),"OCT != OCT");
	}

	@Test
	@DisplayName("BIN to OCT")
	void testFromBINtoOCT() {
		assertEquals("0144", caOCT.ConvertNumber("0b1100100"),"OCT != BIN");
	}

	@Test
	@DisplayName("Engineering type to OCT")
	void testFromENGtoOCT() {
		assertEquals("0144", caOCT.ConvertNumber("1e2"),"Engineering DEC != OCT");
	}

	// To BIN
	@Test
	@DisplayName("DEC to BIN")
	void testFromDECtoBIN() {
		assertEquals("0b1100100", caBIN.ConvertNumber("100"),"BIN != DEC");
	}

	@Test
	@DisplayName("HEX to BIN")
	void testFromHEXtoBIN() {
		assertEquals("0b1100100", caBIN.ConvertNumber("0x64"),"BIN != HEX");
	}

	@Test
	@DisplayName("OCT to BIN")
	void testFromOCTtoBIN() {
		assertEquals("0b1100100", caBIN.ConvertNumber("0144"),"BIN != OCT");
	}

	@Test
	@DisplayName("BIN to BIN")
	void testFromBINtoBIN() {
		assertEquals("0b1100100", caBIN.ConvertNumber("0b1100100"),"BIN != BIN");
	}

	@Test
	@DisplayName("Engineering type to BIN")
	void testFromENGtoBIN() {
		assertEquals("0b1100100", caBIN.ConvertNumber("1e2"),"Engineering DEC != BIN");
	}

	// can't convert
	@Test
	@DisplayName("Impossible DEC conversion")
	void testCantConvertDEC() {
		assertEquals("can't convert", caDEC.ConvertNumber("qwerty"),"Can't convert DEC");
	}

	@Test
	@DisplayName("Impossible HEX conversion")
	void testCantConvertHEX() {
		assertEquals("can't convert", caHEX.ConvertNumber("qwerty"),"Can't convert HEX");
	}

	@Test
	@DisplayName("Impossible OCT conversion")
	void testCantConvertOCT() {
		assertEquals("can't convert", caOCT.ConvertNumber("qwerty"),"Can't convert OCT");
	}

	@Test
	@DisplayName("Impossible BIN conversion")
	void testCantConvertBIN() {
		assertEquals("can't convert", caBIN.ConvertNumber("qwerty"),"Can't convert BIN");
	}
}