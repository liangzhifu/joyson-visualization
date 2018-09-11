package com.joyson.visualization.grade.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joyson.visualization.grade.dao.*;
import com.joyson.visualization.grade.model.*;
import com.joyson.visualization.grade.service.ExportPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by L on 2018/7/26.
 */
@Service
public class ExportPDFServiceImpl implements ExportPDFService {

    @Autowired
    private GradeMonthMapper gradeMonthMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private GradeLineMapper gradeLineMapper;

    @Autowired
    private GradeScoreMapper gradeScoreMapper;

    @Autowired
    private GradeTaskMapper gradeTaskMapper;

    @Override
    public String exportGradeMonthPDF(String id, String path) throws Exception {
        String pdfName;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String randNum = this.getRandNum();
        pdfName = sdf.format(date) + "_" + randNum + ".pdf";

        float marginLeft = 10f;
        float marginRight = 10f;
        float marginTop = 10f;
        float marginBottom = 10f;

        float totalHeigth = 530f;

        //A4 宽595
        Document document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
        PdfWriter.getInstance(document, new FileOutputStream(path + "stdout/" + pdfName));
        document.open();

        //标题
        PdfPTable titleTable = this.getTitleTable(id);
        document.add(titleTable);

        //添加基础信息
        PdfPTable basePdfPTable = this.getBaseTable(id);
        document.add(basePdfPTable);

        // 评价列表
        PdfPTable gradeTable = this.getGradeTable(id);
        document.add(gradeTable);

        //添加一个图片，设置图片的位置，大小
        try {
            Image image = Image.getInstance("C:/Program Files/apache-tomcat-9.0.0.M10/webapps/joyson-visualization/stdout/grade.png");
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
        }catch(Exception e) {
            System.err.println("找不到图片");
        }

        document.close();

        return pdfName;
    }

    /**
     * 做成、确认、承认
     * @param id 生产线班次ID
     * @return 返回结果
     * @throws Exception 异常
     */
    private PdfPTable generateOperateTable(String id) throws Exception {
        GradeMonth gradeMonth = this.gradeMonthMapper.selectByPrimaryKey(id);
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 7, Font.BOLD);

        float trHeight1 = 20f;
        float trHeight2 = 20f;

        float[] columnWidth = {35f, 35f, 35f};
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(columnWidth);
        table.setLockedWidth(true);

        Paragraph paragraph_1_1 = new Paragraph("承认", font);
        paragraph_1_1.setAlignment(1);
        PdfPCell cell_1_1 = new PdfPCell();
        cell_1_1.setUseAscender(true);
        cell_1_1.setUseDescender(true);
        cell_1_1.addElement(paragraph_1_1);
        cell_1_1.setFixedHeight(trHeight1);
        cell_1_1.setVerticalAlignment(5);
        cell_1_1.setHorizontalAlignment(1);
        table.addCell(cell_1_1);

        Paragraph paragraph_1_2 = new Paragraph("确认", font);
        paragraph_1_2.setAlignment(1);
        PdfPCell cell_1_2 = new PdfPCell();
        cell_1_2.setUseAscender(true);
        cell_1_2.setUseDescender(true);
        cell_1_2.addElement(paragraph_1_2);
        cell_1_2.setFixedHeight(trHeight1);
        cell_1_2.setVerticalAlignment(5);
        cell_1_2.setHorizontalAlignment(1);
        table.addCell(cell_1_2);

        Paragraph paragraph_1_3 = new Paragraph("做成", font);
        paragraph_1_3.setAlignment(1);
        PdfPCell cell_1_3 = new PdfPCell();
        cell_1_3.setUseAscender(true);
        cell_1_3.setUseDescender(true);
        cell_1_3.addElement(paragraph_1_3);
        cell_1_3.setFixedHeight(trHeight1);
        cell_1_3.setVerticalAlignment(5);
        cell_1_3.setHorizontalAlignment(1);
        table.addCell(cell_1_3);

        String approveUserName = "";
        GradeTask apprvoeGradeTask = new GradeTask();
        apprvoeGradeTask.setGradeMonthId(gradeMonth.getId());
        apprvoeGradeTask.setTaskStatus(12);
        apprvoeGradeTask.setTaskSeq(2);
        apprvoeGradeTask = this.gradeTaskMapper.selectOne(apprvoeGradeTask);
        if (apprvoeGradeTask != null) {
            Integer userId = apprvoeGradeTask.getUserId();
            if (userId == null) {
                approveUserName = "/";
            } else {
                Userinfo confirmUser = this.userinfoMapper.selectByPrimaryKey(userId);
                approveUserName = confirmUser.getUsername();
            }
        }
        Paragraph paragraph_2_1 = new Paragraph(approveUserName, font);
        paragraph_2_1.setAlignment(1);
        PdfPCell cell_2_1 = new PdfPCell();
        cell_2_1.setUseAscender(true);
        cell_2_1.setUseDescender(true);
        cell_2_1.addElement(paragraph_2_1);
        cell_2_1.setFixedHeight(trHeight2);
        cell_2_1.setVerticalAlignment(5);
        cell_2_1.setHorizontalAlignment(1);
        table.addCell(cell_2_1);

        String confirmUserName = "";
        GradeTask confirmGradeTask = new GradeTask();
        confirmGradeTask.setGradeMonthId(gradeMonth.getId());
        confirmGradeTask.setTaskStatus(12);
        confirmGradeTask.setTaskSeq(1);
        confirmGradeTask = this.gradeTaskMapper.selectOne(confirmGradeTask);
        if (confirmGradeTask != null) {
            Integer userId = confirmGradeTask.getUserId();
            if (userId == null) {
                confirmUserName = "/";
            } else {
                Userinfo confirmUser = this.userinfoMapper.selectByPrimaryKey(userId);
                confirmUserName = confirmUser.getUsername();
            }
        }
        Paragraph paragraph_2_2 = new Paragraph(confirmUserName, font);
        paragraph_2_2.setAlignment(1);
        PdfPCell cell_2_2 = new PdfPCell();
        cell_2_2.setUseAscender(true);
        cell_2_2.setUseDescender(true);
        cell_2_2.addElement(paragraph_2_2);
        cell_2_2.setFixedHeight(trHeight2);
        cell_2_2.setVerticalAlignment(5);
        cell_2_2.setHorizontalAlignment(1);
        table.addCell(cell_2_2);

        Userinfo userinfo = this.userinfoMapper.selectByPrimaryKey(gradeMonth.getUserId());
        Paragraph paragraph_2_3 = new Paragraph(userinfo.getUsername(), font);
        paragraph_2_3.setAlignment(1);
        PdfPCell cell_2_3 = new PdfPCell();
        cell_2_3.setUseAscender(true);
        cell_2_3.setUseDescender(true);
        cell_2_3.addElement(paragraph_2_3);
        cell_2_3.setFixedHeight(trHeight2);
        cell_2_3.setVerticalAlignment(5);
        cell_2_3.setHorizontalAlignment(1);
        table.addCell(cell_2_3);

        return table;
    }

    /**
     * 获取标题
     * @return
     * @throws Exception
     */
    public PdfPTable getTitleTable(String id) throws Exception{
        GradeMonth gradeMonth = this.gradeMonthMapper.selectByPrimaryKey(id);
        float[] columnWidth = {400f, 120f};
        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(10f);
        table.setTotalWidth(columnWidth);
        table.setLockedWidth(true);

        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 20, Font.BOLD);
        Paragraph paragraph = new Paragraph("生产岗位评价表" + "   " + gradeMonth.getMonth(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell_1_1 = new PdfPCell();
        cell_1_1.setUseAscender(true);
        cell_1_1.setUseDescender(true);
        cell_1_1.addElement(paragraph);
        cell_1_1.setBorder(0);
        table.addCell(cell_1_1);

        PdfPTable operateTable = this.generateOperateTable(id);
        PdfPCell cell_1_2 = new PdfPCell();
        cell_1_2.setUseAscender(true);
        cell_1_2.setUseDescender(true);
        cell_1_2.addElement(operateTable);
        cell_1_2.setBorder(0);
        table.addCell(cell_1_2);

        return table;
    }

    public PdfPTable getGradeTable(String id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        GradeMonth gradeMonth = this.gradeMonthMapper.selectByPrimaryKey(id);

        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12, Font.BOLD);
        Font font2 = new Font(bfChinese, 12, Font.BOLD);
        BaseColor color = new BaseColor(0, 100, 0);
        font2.setColor(color);

        // 查询评价打分生产线
        Map<String, Object> map = new HashMap<>();
        map.put("lineId", gradeMonth.getLineId());
        map.put("shiftName", gradeMonth.getShiftName());
        map.put("beginDateBegin", gradeMonth.getMonth()+"-01");
        map.put("beginDateEnd", gradeMonth.getMonth()+"-31");
        List<GradeLine> gradeLineList = this.gradeLineMapper.selectMonthGradeLine(map);
        int num = gradeLineList.size();
        float[] columnWidth = new float[2 + num];
        int i;
        columnWidth[0] = 50f;
        columnWidth[1] = 525f - 40 * num ;
        for(i = 2; i < 2 + num; i++){
            columnWidth[i] = 40;
        }
        PdfPTable table = new PdfPTable(2 + num);
        table.setSpacingBefore(3f);
        table.setTotalWidth(columnWidth);
        table.setLockedWidth(true);

        Paragraph paragraph1 = new Paragraph("姓名：", font);
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setSpacingBefore(15);
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(10);
        cell.setPaddingTop(10);
        cell.addElement(paragraph1);
        table.addCell(cell);

        Paragraph paragraph2 = new Paragraph("岗位：", font);
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        paragraph2.setSpacingBefore(15);
        PdfPCell cel2 = new PdfPCell();
        cel2.setUseAscender(true);
        cel2.setUseDescender(true);
        cel2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel2.setPaddingBottom(10);
        cel2.setPaddingTop(10);
        cel2.addElement(paragraph2);
        table.addCell(cel2);

        for(i = 2; i < 2 + num; i++){
            GradeLine gradeLine = gradeLineList.get(i - 2);
            Date beginDate = gradeLine.getBeginDate();
            Date endDate = gradeLine.getEndDate();
            Paragraph paragraph = new Paragraph(sdf.format(beginDate) + "~" + sdf.format(endDate), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cel = new PdfPCell();
            cel.setUseAscender(true);
            cel.setUseDescender(true);
            cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cel.setPaddingBottom(10);
            cel.setPaddingTop(10);
            cel.addElement(paragraph);
            table.addCell(cel);
        }

        // 查询用户
        Long gradeLineId = gradeLineList.get(0).getId();
        Condition condition = new Condition(GradeScore.class);
        condition.createCriteria().andEqualTo("gradeLineId", gradeLineId);
        List<GradeScore> gradeScoreList = this.gradeScoreMapper.selectByCondition(condition);
        for (GradeScore gradeScore : gradeScoreList) {
            Integer userId = gradeScore.getUserId();
            Userinfo userinfo = this.userinfoMapper.selectByPrimaryKey(userId);

            paragraph1 = new Paragraph(userinfo.getUsername(), font);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setSpacingBefore(15);
            cell = new PdfPCell();
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPaddingBottom(10);
            cell.setPaddingTop(10);
            cell.addElement(paragraph1);
            table.addCell(cell);

            List<Map<String, Object>> mapList = this.userinfoMapper.selectPostInfo(userId);
            Phrase phrase = new Phrase();
            for (Map map1 : mapList) {
                Boolean MainPost = (Boolean) map1.get("MainPost");
                if (MainPost) {
                    String Important = (String) map1.get("Important");
                    if ("1".equals(Important)) {
                        //Image image = Image.getInstance("C:/Program Files/apache-tomcat-9.0.0.M10/webapps/joyson-visualization/stdout/star.png");
                        Image image = Image.getInstance("D:/Program Files/apache-tomcat-9.0.0.M10/webapps/visualization/stdout/star.png");
                        image.setAlignment(Image.ALIGN_CENTER);
                        phrase.add(image);
                        Chunk chunk = new Chunk((String) map1.get("PostName") + "；", font2);
                        phrase.add(chunk);
                    } else {
                        Chunk chunk = new Chunk((String) map1.get("PostName") + "；", font2);
                        phrase.add(chunk);
                    }
                } else {
                    Chunk chunk = new Chunk((String) map1.get("PostName") + "；", font);
                    phrase.add(chunk);
                }
            }
            cel2 = new PdfPCell();
            cel2.setUseAscender(true);
            cel2.setUseDescender(true);
            cel2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cel2.setPaddingBottom(10);
            cel2.setPaddingTop(10);
            cel2.addElement(phrase);
            table.addCell(cel2);

            for(i = 2; i < 2 + num; i++) {
                GradeLine gradeLine = gradeLineList.get(i - 2);
                GradeScore gradeScoreTemp = new GradeScore();
                gradeScoreTemp.setGradeLineId(gradeLine.getId());
                gradeScoreTemp.setUserId(userId);
                gradeScoreTemp = this.gradeScoreMapper.selectOne(gradeScoreTemp);
                if (gradeScoreTemp == null) {
                    Paragraph paragraph = new Paragraph("", font);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingBefore(15);
                    PdfPCell cel = new PdfPCell();
                    cel.setUseAscender(true);
                    cel.setUseDescender(true);
                    cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cel.setPaddingBottom(10);
                    cel.setPaddingTop(10);
                    cel.addElement(paragraph);
                    table.addCell(cel);
                } else {
                    PdfPCell cel = new PdfPCell();
                    cel.setUseAscender(true);
                    cel.setUseDescender(true);
                    cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cel.setPaddingBottom(10);
                    cel.setPaddingTop(10);
                    if (gradeScoreTemp.getScore() == null || "".equals(gradeScoreTemp.getScore())) {
                        Paragraph paragraph = new Paragraph("", font);
                        paragraph.setAlignment(Element.ALIGN_CENTER);
                        paragraph.setSpacingBefore(15);
                        cel.addElement(paragraph);
                    } else if ("良好".equals(gradeScoreTemp.getScore())) {
                        //Image image = Image.getInstance("C:/Program Files/apache-tomcat-9.0.0.M10/webapps/joyson-visualization/stdout/green.png");
                        Image image = Image.getInstance("D:/Program Files/apache-tomcat-9.0.0.M10/webapps/visualization/stdout/green.png");
                        image.setAlignment(Image.ALIGN_CENTER);
                        cel.addElement(image);
                    } else if ("一般".equals(gradeScoreTemp.getScore())) {
                        //Image image = Image.getInstance("C:/Program Files/apache-tomcat-9.0.0.M10/webapps/joyson-visualization/stdout/yellow.png");
                        Image image = Image.getInstance("D:/Program Files/apache-tomcat-9.0.0.M10/webapps/visualization/stdout/yellow.png");
                        image.setAlignment(Image.ALIGN_CENTER);
                        cel.addElement(image);
                    } else if ("不良".equals(gradeScoreTemp.getScore())) {
                        //Image image = Image.getInstance("C:/Program Files/apache-tomcat-9.0.0.M10/webapps/joyson-visualization/stdout/red.png");
                        Image image = Image.getInstance("D:/Program Files/apache-tomcat-9.0.0.M10/webapps/visualization/stdout/red.png");
                        image.setAlignment(Image.ALIGN_CENTER);
                        cel.addElement(image);
                    } else {
                        Paragraph paragraph = new Paragraph(gradeScoreTemp.getScore(), font);
                        paragraph.setAlignment(Element.ALIGN_CENTER);
                        paragraph.setSpacingBefore(15);
                        cel.addElement(paragraph);
                    }
                    table.addCell(cel);
                }
            }
        }
        return table;
    }

    /**
     * 基础数据
     * @param id
     * @return
     * @throws Exception
     */
    public PdfPTable getBaseTable(String id) throws Exception{
        GradeMonth gradeMonth = this.gradeMonthMapper.selectByPrimaryKey(id);

        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12, Font.BOLD);

        float[] columnWidth = new float[4];
        int i;
        for(i = 0; i < 4; i++){
            columnWidth[i] = 575f / 4;
        }
        PdfPTable table = new PdfPTable(4);
        table.setSpacingBefore(3f);
        table.setTotalWidth(columnWidth);
        table.setLockedWidth(true);

        Paragraph paragraph1 = new Paragraph("生产线：" + gradeMonth.getDeptName(), font);
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setSpacingBefore(15);
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(10);
        cell.setPaddingTop(10);
        cell.addElement(paragraph1);
        table.addCell(cell);

        Paragraph paragraph2 = new Paragraph("班次：" + gradeMonth.getShiftName(), font);
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        paragraph2.setSpacingBefore(15);
        PdfPCell cel2 = new PdfPCell();
        cel2.setUseAscender(true);
        cel2.setUseDescender(true);
        cel2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel2.setPaddingBottom(10);
        cel2.setPaddingTop(10);
        cel2.addElement(paragraph2);
        table.addCell(cel2);

        Userinfo userinfo = this.userinfoMapper.selectByPrimaryKey(gradeMonth.getUserId());
        Paragraph paragraph3 = new Paragraph("线长：" + userinfo.getUsername(), font);
        paragraph3.setAlignment(Element.ALIGN_CENTER);
        paragraph3.setSpacingBefore(15);
        PdfPCell cel3 = new PdfPCell();
        cel3.setUseAscender(true);
        cel3.setUseDescender(true);
        cel3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel3.setPaddingBottom(10);
        cel3.setPaddingTop(10);
        cel3.addElement(paragraph3);
        table.addCell(cel3);

        Paragraph paragraph4 = new Paragraph("月份：" + gradeMonth.getMonth(), font);
        paragraph4.setAlignment(Element.ALIGN_CENTER);
        paragraph4.setSpacingBefore(15);
        PdfPCell cel4 = new PdfPCell();
        cel4.setUseAscender(true);
        cel4.setUseDescender(true);
        cel4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel4.setPaddingBottom(10);
        cel4.setPaddingTop(10);
        cel4.addElement(paragraph4);
        table.addCell(cel4);
        return table;
    }

    private String getRandNum(){
        Random random = new Random();
        String result = "";
        for(int i = 0; i < 6; i++){
            result+=random.nextInt(10);
        }
        return result;
    }
}
