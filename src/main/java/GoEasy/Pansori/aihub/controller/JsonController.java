//package GoEasy.Pansori.aihub.controller;
//
//import GoEasy.Pansori.aihub.repository.AiHubRepository;
//import GoEasy.Pansori.aihub.domain.Document;
//import lombok.RequiredArgsConstructor;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.transaction.Transactional;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Transactional
//public class JsonController {
//    private final AiHubRepository jsonToDB;
//    @GetMapping("/insert")
//    public List<Document> jsonparser() throws Exception{
//        String text;
//        List<Document> documentList = new ArrayList<>();
////        List<Text> textList;
////        Text text;
////        List<TextSection> textSectionList;
////        TextSection textSection;
//        Document document;
//        String result = "";
//
////        Object ob = new JSONParser().parse
//        BufferedReader reader =  new BufferedReader(new InputStreamReader(new FileInputStream("/Users/olzlg1/Downloads/문서요약 텍스트/Validation/valid_original.json"), "UTF-8"));
//
//        JSONObject jb = (JSONObject) new JSONParser().parse(reader);
//
//        JSONArray jsonArr = (JSONArray) jb.get("documents");
//
//        for(int i=0; i<jsonArr.size(); i++) {
//            text = "";
//            document = new Document();
//
//            JSONObject pr = (JSONObject) jsonArr.get(i);
//
////            documents.setDocument_id(pr.get("id").toString());
//
//            document.setCategory(pr.get("category").toString());
//
////            documents.setSize(pr.get("size").toString());
//
////            documents.setChar_count((Long) pr.get("char_count"));
//
//            document.setPublish_date(pr.get("publish_date").toString());
//
//            document.setTitle(pr.get("title").toString());
//
//            JSONArray textarr = (JSONArray) pr.get("text");
//            for ( int textindex = 0; textindex < textarr.size(); textindex++){
//                JSONArray textarr2 = (JSONArray) textarr.get(textindex);
//                for (int textindex2 = 0; textindex2 < textarr2.size(); textindex2++) {
//                    JSONObject textarr3 = (JSONObject) textarr2.get(textindex2);
//                    text += textarr3.get("sentence");
//                }
//            }
//            document.setContent(text);
////            documents.setText(pr.get("text").toString());
////
////            textList = new ArrayList<>();
////            for(int j = 0; j<textarr.size(); j++) {
////                JSONArray textarr2 = (JSONArray) textarr.get(j);
////                text = new Text();
////
////                // * Get text section
////                textSectionList = new ArrayList<>();
////                for(int k = 0; k < textarr2.size(); k++){
////                    JSONObject te = (JSONObject) textarr2.get(k);
////                    textSection = new TextSection();
////                    textSection.setIndex((Long) te.get("index"));
////                    textSection.setSentence(te.get("sentence").toString());
////                    textSection.setHighlight_indices(("highlight_indices").toString());
////                    textSectionList.add(textSection);
////                }
////
//////                response.setCharacterEncoding("UTF-8");
////
////
////                // * Get text
////                // text.setTextSections(textSectionList);
////                text.setTextSections(textSectionList);
////                textList.add(text);
////            }
////            documents.setText(textList);
////            documents.setAnnotatior_id((Long) pr.get("annotator_id"));
////            documents.setDocumentScore(pr.get("document_quality_scores").toString());
////            documents.setExtractive(pr.get("extractive").toString());
//            JSONArray abs = (JSONArray) pr.get("abstractive");
//            document.setAbstractive(abs.get(0).toString());
////            documents.setCount(0);
//            documentList.add(document);
//            jsonToDB.save(document);
////        }
//        }
//        return documentList;
//    }
//}
