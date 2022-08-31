//package GoEasy.Pansori.function;
//
//import GoEasy.Pansori.api.domain.DetailPrecedent;
//import GoEasy.Pansori.domain.DetailPrecedent;
//import GoEasy.Pansori.service.DetailPrecService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import javax.print.DocFlavor;
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@SpringBootTest
//@Transactional
//public class FunctionTest {
//
//    @Autowired
//    private DetailPrecService detailPrecService;
//
//    @Test
//    public void 판례_본문데이터_파싱테스트() throws Exception {
//
//
//        String content = "【피 고 인】 【상 고 인】 피고인【원심판결】 서울서부지법 2008. 7. 17. 선고 2008노466 판결【주    문】  상고를 기각한다.【이    유】  상고이유를 판단한다.  1.         골프와 같은 개인 운동경기에 참가하는 자는 자신의 행동으로 인해 다른 사람이 다칠 수도 있으므로, 경기 규칙을 준수하고 주위를 살펴 상해의 결과가 발생하는 것을 미연에 방지해야 할 주의의무가 있고, 이러한 주의의무는 경기보조원에 대하여도 마찬가지이다. 다만, 운동경기에 참가하는 자가 경기규칙을 준수하는 중에 또는 그 경기의 성격상 당연히 예상되는 정도의 경미한 규칙위반 속에 상해의 결과를 발생시킨 것으로서 사회적 상당성의 범위를 벗어나지 아니하는 행위라면 과실치상죄가 성립하지 않는다고 할 것이지만, 골프경기를 하던 중 골프공을 쳐서 아무도 예상하지 못한 자신의 등 뒤편으로 보내어 등 뒤에 있던 경기보조원(캐디)에게 상해를 입힌 경우에는 주의의무를 현저히 위반한 사회적 상당성의 범위를 벗어난 행위로서 과실치상죄가 성립한다.  같은 취지에서 원심이 채용 증거를 종합하여 피고인이 골프장에서 골프경기를 하던 중 피고인의 등 뒤 8m 정도 떨어져 있던 경기보조원을 골프공으로 맞혀 상해를 입힌 사실을 인정하여 과실치상죄를 인정하고, 피해자가 경기보조원으로서 통상 공이 날아가는 방향이 아닌 피고인 뒤쪽에서 경기를 보조하는 등 경기보조원으로서의 기본적인 주의의무를 마친 상태였고, 자신이 골프경기 도중 상해를 입으리라고 쉽게 예견하였을 것으로 보이지 않으므로, 피해자의 명시적 혹은 묵시적 승낙이 있었다고 보기 어렵다는 이유로 위법성이 조각된다는 피고인의 주장을 배척한 것은 사실심 법관의 합리적인 자유심증에 따른 것으로서 정당하고 거기에 상고이유로 주장하는 바와 같은 채증법칙 위반, 법리오해 등의 위법이 없다.  2. 반의사불벌죄에 있어 피해자의 처벌을 희망하지 아니하는 의사표시나 처벌을 희망하는 의사표시의 철회는 제1심판결 선고 전까지 할 수 있는 것인바, 기록에 의하면, 피해자가 제1심판결 선고 전까지 피고인에 대한 처벌을 희망하지 아니하는 의사표시를 하였거나 처벌을 희망하는 의사표시를 철회하였음을 인정할 자료를 찾아 볼 수 없으므로, 이 부분 상고이유의 주장은 받아들이지 아니한다.  3. 그리고         형사소송법 제383조 제4호의 규정에 의하면 사형, 무기 또는 10년 이상의 징역이나 금고가 선고된 사건에 있어서만 양형부당을 사유로 한 상고가 허용되는 것이므로, 피고인에게 그보다 가벼운 벌금형이 선고된 이 사건에 있어서는 형의 양정이 부당하다는 사유는 적법한 상고이유가 되지 못한다.        4. 그러므로 상고를 기각하기로 하여, 관여 대법관의 일치된 의견으로 주문과 같이 판결한다.대법관 김영란(재판장) 이홍훈 안대희(주심) 양창수 ";
//
//        String[] strArr = content.split("【");
//
//
//        for (String splitStr : strArr) {
//            String[] splitResult = splitStr.split("】");
//
//            if (strArr[strArr.length - 1] == splitStr) { //마지막 SplitStr일 경우
//                String precContent = splitResult[1].substring(0, splitResult[1].lastIndexOf(".") + 1); //본문 이유 데이터
//                String judgePeople = splitResult[1].substring(splitResult[1].lastIndexOf(".") + 1); //본문 판사 데이터
//
//                System.out.println("test");
//                System.out.println(precContent);
//
//
//                String[] judgeArr = judgePeople.split(" ");
//                String[] strArrTemp = new String[2];
//                for (int j = 1; j < judgeArr.length; j++) {
//                    if (judgeArr[j].indexOf("(") != -1) {
//                        strArrTemp[0] = judgeArr[j].substring(0, judgeArr[j].indexOf("("));
//                        strArrTemp[1] = judgeArr[j].substring(judgeArr[j].indexOf("(") + 1, judgeArr[j].length() - 1);
//                        System.out.println(strArrTemp[1] + " : " + strArrTemp[0]);
//                    } else {
//                        System.out.println(judgeArr[j]);
//                    }
//                }
//                return;
//            }
//
//            if (splitResult.length == 1) {
//                System.out.println(splitResult[0]);
//            } else {
//                System.out.println(splitResult[0] + ":" + splitResult[1]);
//            }
//        }
//
//    }
//
//    @Test
//    public void 판례본문_파싱테스트2() throws Exception {
//
//
//        String content = "【원고, 상고인】 【피고, 피상고인】 종로세무서장외 1인(소송대리인 변호사 진행섭)【원심판결】 서울고법 2006. 9. 15. 선고 2005누22946 판결【주 문】 상고를 기각한다. 상고비용은 원고가 부담한다.【이 유】 상고이유를 판단한다. 1. 업무무관 가지급금 등에 관한 지급이자 손금 불산입에 대하여(상고이유 제1점 내지 제5점)구 법인세법(1998. 12. 28. 법률 제5581호로 전문 개정되기 전의 것, 이하 ‘구 법인세법’이라 한다) 제18조의3 제1항 제3호, 같은 법 시행령(1998. 12. 31. 대통령령 제15970호로 전문 개정되기 전의 것, 이하 ‘구 법인세법 시행령’이라 한다) 제43조의2 제2항 제2호와 법인세법 제28조 제1항 제4호 (나)목, 같은 법 시행령 제53조 제1항, 제2항에서 규정한 ‘업무와 관련 없이 지급한 가지급금 등’에는 순수한 의미의 대여금은 물론, 채권의 성질상 대여금에 준하는 것도 포함되고, 적정한 이자율에 의하여 이자를 받으면서 가지급금을 제공한 경우도 포함되며, 가지급금의 업무관련성 여부는 당해 법인의 목적이나 영업내용을 기준으로 객관적으로 판단하여야 한다 ( 대법원 1992. 11. 10. 선고 91누8302 판결, 대법원 2007. 9. 20. 선고 2006두1647 판결 등 참조). 원심은 그 채용 증거를 종합하여 판시와 같은 사실을 인정한 다음, 소외 1 주식회사의 목적 사업, 소외 1 주식회사가 이 사건 후순위사채를 매입할 당시의 재무상태, 당시 3년 만기 회사채(AA- 등급)의 수익률, 국세청장이 정한 당좌대월이자율 및 소외 1 주식회사 발행 회사채 이자율 등과 이 사건 후순위사채의 이자율과의 차이, 소외 2 주식회사가 이 사건 후순위사채를 발행한 목적 및 경위, 이 사건 후순위사채로 인해 소외 2 주식회사가 얻는 경제적 이익, 당시 공모에 의하지 않고 ○○ 계열기업들만이 소외 2 주식회사와의 계약으로 이 사건 후순위사채를 포함한 후순위사채를 인수하였던 점, 소외 1 주식회사는 이 사건 후순위사채와 유사한 다른 금융상품에 여유자금을 투자한 바 없었던 점, 소외 1 주식회사가 이자 수익을 목적으로 금전을 대여하는 금융업을 영위하였음을 인정할 증거가 없고, 소외 1 주식회사를 흡수합병한 원고 역시 법인등기부에 ‘금융업’을 목적사업의 하나로 명시하고 있기는 하나 실제 금융업을 영위하였음을 인정할 증거는 없는 점 등에 비추어 보면, 소외 1 주식회사나 이를 흡수합병한 원고가 소외 2 주식회사 발행의 이 사건 후순위사채를 인수하여 보유한 것은 특수관계자인 소외 2 주식회사에 재무구조 개선에 필요한 자금을 지원하기 위한 것으로서, 유가증권의 매입이라는 형식에 불구하고 실질적으로는 자금을 대여한 것이므로, ‘업무와 관련 없이 지급한 가지급금 등’에 해당한다고 판단하였다. 앞서 본 법리와 기록에 비추어 살펴보면, 원심의 이러한 인정과 판단은 정당하고, 상고이유 주장과 같은 업무무관 가지급금 등에 관한 법리오해, 판단유탈, 심리미진 등의 위법이 없다. 그리고 원고는, 원심이 갑 제4호증 등 관련 증거를 배척하고, 소외 1 주식회사가 이 사건 후순위사채를 계속 보유하기로 결정할 당시 발행회사인 소외 2 주식회사와 사이에 이 사건 후순위사채를 만기 전에 환매받을 수 있도록 하는 수시상환특약을 체결하였다는 사실을 인정하지 않은 것은 처분문서의 증명력에 관한 법리를 오해한 위법이 있다고 다투나, 앞서 본 사정에 비추어 보면, 이 사건 후순위사채에 그 주장과 같은 수시상환특약이 있었는지 여부는 이 사건 후순위사채의 인수·보유가 소외 2 주식회사에 대한 자금지원으로서 ‘업무와 관련 없이 지급한 가지급금 등’에 해당한다는 점을 인정하는 데에 아무런 장애가 되지 아니하므로, 이 부분 주장은 더 나아가 살펴볼 것도 없이 받아들일 수 없다. 2. 부당행위계산의 부인에 대하여(상고이유 제6점)구 법인세법 제20조 소정의 부당행위계산 부인이란, 법인이 특수관계에 있는 자와의 거래에서 정상적인 경제인의 합리적인 방법에 의하지 아니하고 구 법인세법 시행령 제46조 제2항 각 호에 열거된 제반 거래형태를 빙자하여 남용함으로써 조세부담을 부당하게 회피하거나 경감시켰다고 인정하는 경우에 과세권자가 이를 부인하고 법령에 정하는 방법에 의하여 객관적이고 타당하다고 보이는 소득이 있는 것으로 의제하는 제도로서, 이는 거래행위의 제반 사정을 구체적으로 고려하여 그 거래행위가 건전한 사회통념이나 상관행에 비추어 경제적 합리성을 결한 비정상적인 것인지의 여부에 따라 판단하되( 대법원 2006. 5. 11. 선고 2004두7993 판결 등 참조), 그 판단 시기는 거래행위 당시를 기준으로 하여야 한다( 대법원 2007. 9. 20. 선고 2006두1647 판결 등 참조). 원심판결 이유에 의하면, 원심은 앞서 본 사실관계에 터 잡아 소외 1 주식회사가 국세청장 고시의 당좌대월이자율보다도 낮은 이자율로 이 사건 후순위사채를 인수·보유하기로 한 것은 경제적 합리성을 결한 비정상적인 것으로서 부당행위계산 부인의 대상이 되고, 따라서 피고 종로세무서장이 그 때부터 이 사건 후순위사채의 이자율이 당좌대월이자율보다 상회하기 직전인 1998. 9. 30.까지의 기간에 대해 구 법인세법 시행령 제47조 제2항 본문을 적용하여 같은 기간 소외 1 주식회사의 차입금 이자율 중 높은 것과 이 사건 후순위사채의 이자율과의 차액에 해당하는 금액을 인정이자로 계산하여 익금산입한 것은 적법하다고 판단하였다. 앞의 법리와 기록에 비추어 살펴보면, 원심의 이러한 판단은 옳고, 부당행위계산 부인의 법리를 오해하여 그 판단대상과 부당행위계산 기간을 잘못 산정하였다는 등의 상고이유 주장과 같은 위법이 없다. 3. 결 론 그러므로 상고를 기각하고, 상고비용은 패소자가 부담하게 하기로 관여 대법관의 의견이 일치되어 주문과 같이 판결한다.대법관 안대희(재판장) 김영란(주심) 이홍훈 양창수";
//
//        String[] strarr = content.split("【");
//        String[] strtemp;
//        String[] strtemp2 = new String[2];
//
//
//        for (int i = 0; i < strarr.length; i++) {
//            strtemp = strarr[i].split("】");
//
//            if (i == strarr.length - 1) {
//                String precContent = strtemp[1].substring(0, strtemp[1].lastIndexOf("."));
//                String persons = strtemp[1].substring(strtemp[1].lastIndexOf("."));
//
//                strtemp = persons.split(" ");
//                for (int j = 1; j < strtemp.length; j++) {
//                    if (strtemp[j].indexOf("(") != -1) {
//                        strtemp2[0] = strtemp[j].substring(0, strtemp[j].indexOf("("));
//                        strtemp2[1] = strtemp[j].substring(strtemp[j].indexOf("(") + 1, strtemp[j].length() - 1);
//                        System.out.println(strtemp2[0] + " : " + strtemp2[1]);
//                    }
//                    System.out.println(strtemp[j]);
//                }
//
//                System.out.println(precContent);
//                return;
//            }
//
//            if (strtemp.length == 1) {
//                System.out.println(strtemp[0]);
//            } else {
//                System.out.println(strtemp[0] + ":" + strtemp[1]);
//            }
//
//
//        }
//        return;
//    }
//
//
//    @Test
//    @Rollback(value = false)
//    public void 판례본문파싱테스트() throws Exception {
//
//
//        List<DetailPrecedent> precedents = detailPrecService.findAll();
//
//        int offset = 0;
//
//        for (int i = offset; i < precedents.size(); i++) {
//            DetailPrecedent precedent = detailPrecService.findById(precedents.get(i).getId());
//
//            System.out.println("PREC_ID : " + precedent.getId());
//            System.out.println("ORDER_ID : " + i);
//
//            parsingContent(precedent);
//        }
//
//    }
//
//    @Test
//    public void parsingContent(DetailPrecedent detailPrecedent){
//
//        String content = detailPrecedent.getPrecContent();
//
////        String content = "【원고, 피상고인】   정신기  우소송대리인 변호사 손석도【피고, 상고인】   전경근  우소송대리인 변호사 김재천【원심판결】 제1심 광주지방법원, 제2심         광주고등법원 1954. 3. 14 선고 52민공7 판결【주    문】  본건 상고를 기각한다  상고 소송비용은 피고의 부담으로 한다【이    유】  피고 대리인 상고이유 제1점은 원판결은 채권담보에 대한 각 종의 법률견해를 오진한 위법의 판결이니 이의 취소를 불면하다 원심판결의 판시한 총괄적 이유를 규지하여 본다면 본건 청구원인계약의 양도매매담보라 하였지만 현하 법제도상 금전소비대차계약상 채권을 담보하고 있는데 대한 담보로서의 종류는 (1) 담보목적물의 대가를 정하고 소유권을 채권자에게 일응 양도적으로 매도하여 놓고 기일경과후 이행이 없는 경우에는 소유권 이전등기를 하여준다는 계약의 요지에 특약이 있는 경우에 하나가 있으며 (2) 대물변제예약으로 기한에 이행이 없으면 당연히 담보목적물의 소유권이전등기하여 준다는 계약당초부터 특약적 예약이 있는 경우에 둘째이며 (3) 대물변제계약으로 기한에 이행이 없으면 담보목적물 그 기한경과와 동시에 당하여 새로이 대물변제계약으로 소유권이전등기하여 주는 경우에 셋째가 있으며 (4) 채권담보적으로 담보약정을 하여놓고 기한의 경과 특히 목적물의 소유권 이전등기를 하지 않고 채권을 담보케 하는 경우에 보통담보계약의 넷째가 있을 것이며 (5) 기타 경우에 의하여 유저당권설정계약으로 담보물로 채권자에게 소유권이전등기 하여주는 등등의 종류를 상상할 수 있다 그러므로 채권담보에는 (1) 양도매도특약 (2) 계약당초부터 대물변제예약 (3) 변제시에 새로이 대물변제특약 (4) 보통담보계약 (5) 유저당설정행위등을 원인으로 하는 경우라도 여하한 담보채권의 종류를 물론하고 채권액과 목적물은 계약상확정이 되어지는 바 확정된 채권액을 채무자에게 전액을 대행하지 않은 이상 기확정되어 있는 담보목적물을 취득 또는 타의 방법으로 채무변제의 처리에 적용불능하는 것은 언을 불사할 것이다 그런데 본건의 채권담보 내용에 의사해석상의 종류를 언급한다 하면 전기 (1) 의 양도 매도담보계약이 아니고 전기 (4) 보통채권담보계약에 속한 종류의 소비대차담보에 속한 것이며 또 확정된 채권액을 소위 예약금 1천만 원 한도로 확정된 목적물은 본건 대지 및 건물이다 원심판결이유중 「우 합계 79만 원에 대하여는 그 담보로서 피고 소유의 본건 건물 및 대지를 원고에게 제공하고 만일 우기한내에 우각 원리금의 지불이 없는 경우에는 원고 명의로 매도 담보를 원인으로 하는 소유권 이전등기 절차를 이행하고」 운운 판시하였으나 전기(1)에 속한 매도담보가 아닌것은 을 제1호증 각서를 본다 할 지라도 매도담보라 계약문언이 무하고 기한이 경과하면 소유권등기로서 이전등기절차를 한다는 특약의 문언을 발견할 수 없는데 이를 인정한 것은 계약사실이 없는 사실을 사실로 하고 없는 증거로 사실을 인정하였으니 위법이다 그러므로 전기 (4) 에 속하는 보통담보의 종류에 속한 것이라는 것 이 동각서의 문언으로 보아 당사자의 보통의 의사해석이라고 할 것이다 번복하여서 본건계약의 내용사실의 진실을 관찰한다면 피고는 단기 1950년 3월 1일 소외         1과의 간에 동인으로부터 원본상업자금으로 계약상 확정된 금 1천만 원을 융자받기로 약속하고 기예약금(금 1천만 원 한도) 중의 일부로서 소외         1로부터 우선 금 55만 원(        소외 1이 진출한 약속수형으로) 차용함에 있어 이자는 월 1할, 변제기는 동년 4월 말일로 약정하고 피고가 별도 부담금 24만 원을 합하여 채무를 부담하고 기예약금 1천만 원의 계약상 확정된 담보로 본건가대에 대한 가등기만을 경유한 사실은 을 제1호증 각서와         소외 1의 일부증언         소외 2의 증언으로써 인정할 수 있고 갑 제1호증 96만 원 약속수형과 갑 제2호증 토지건물매도증서는 피고 전경근으로부터 교부받은 전경근의 인장으로         소외 1이 전경근의 승낙없이 사법서사         소외 3에게 위탁하여 위조하였던 사실에 대한 증거는 을 제3호증의 5의 증인 사법서사         소외 3이 광주지방검찰청 신문시에 공술한 증언에 의하여 판명이 되고 있는데도 불구하고 원심은 차를 간과하고 증거를 유탈하여 양도매도담보라고 감히 인정하였으니 차는 오판이 되었고 가사양도매도담보계약이라 할지라도 계약상 확정된 한도채권액 금 1천만 원 이나 기예약금일부의 금 55만 원 (구화) 만을 대차하여 놓고 계약상 확정된 채권액인 예납금 1천만 원에 대한 본건 담보목적물의 소유권을 취득하라는 것은 신의성실의 원칙과 형평의 원칙에 위반이니 차를 인정할 것이 아니고 양도매도계약의 원인으로 본건 목적물을 취득하려면 을 제1호증 각서의 계약의 취지대로 기「예납금 1천만 원」전부를 융자하여준 뒤에 피고가 기한에 이행하지 않는 경우에서 발생할 문제이지 자기채무액인 금 1천만 원의 융자를 일시적으로나 수회에 계속적으로 하여 주지 않고 하는 기반대급부인 본건청구는 위법이다 또 을 제3호증의7에 의하면 갑 제1호증 96만 원의 약속수형 갑 제2호증 갑 제3호증에 대하여 증인         소외 1이 권리 포기를 하였는데 차의 내용과         소외 1의 증언이 허위사실로 상위가 있어 조신할 수 없음에도 불구하고 원판결은 이를 간과 일탈하고 상호모순당착이 된 동인의 증언을 인정의 자료에 공하였으니 원판결은 위법이라 운함에 있고 동 상고이유 제6점은 원심판결은 증거를 유탈하여 채증법칙에 위배된 것으로 기인되어 사실오인이 되었으니 심리미진이 아니면 이유불비이며 또 증거법 위배가 되었다 기 이유를 좌기의 각각 서증에 대하여 논박하고저 한다 각 서증중 갑 제1호 및 제2호증은 전경근의 승낙없이         소외 1이 사법서사         소외 3으로 하여금 자유로 작성한 것이고 원판결이유에 유탈되었으니 자에 논진을 생략함 (1) 을 제1호증         소외 1이 단기 1950년 3월 1일자로 전경근에게 차출한 「각서」 에는 「단 귀하의 소유 광주시 금남로5가         (번지 생략) 대 88평 급 건물전부를 단기 1950년 3월 2일부로 정신기의 명의로 매매가등기를 수한 바 동예납금중 55만 원(신화 5,500원) 정은 3월말까지 접수하기로 하고 24만 원정은 한 3개월 이내로 병리하여 청산이 유한 시는 본 매매등기 일절권리를 환원키로 이증서를 차입함」 한다고 하였을 뿐이지 기한이 경과한다면 본건 대지 및 건물을 대물변제특약이나 혹은 대물변제예약으로나 또는 양도담보로 혹은 유저당으로 기본적 소유권을 이전등기하여 준다는 특약표시를 한 문언의미는 없다 더구나 동 각서내용의 문서 자체의 당사자 간의 의사해석으로 관찰하여 본다 할지라도 기계약의 원인된 법률요건이 판시한대로 양도매도담보란 의미의 문언을 발견할 수 없다 그 각서 내용에 「동 예약금중 「운운의 예약금이라 하는 것은 금 1천만 원을 한도로 하여 융자하겠다 함으로 보통채권담보로 가등기만을 하여준 것이지 금 55만 원 (신화 5,500환)에만 한한 가등기가 아니다 그러므로 예약금이라는 것은 금 1천만 원을 운위한 것이다 그러므로 본건 가등기는 예약금 1천만 원 한도의 소비대차담보가등기 뿐이지 본건 목적물의 양도매매담보도 아니고 대물변제예약도 아니오 또 변제기에 와서 하는 대물변제계약도 아니고 유저당도 아니다 (2) 을 제2호증의 1,2,3의 영수증에 표시된 문의를 본다면 수형금 55만 원 (신화 5,500환) 에 대한 3개월분 이자를 지불한 사실로 본다 할지라도 암묵의 무기연기를 하였다는 사실을 인정함에 충분하고 양도매도계약이 있었다는 사실을 인정할 수 없다 (3) 을 제3호증의 1의 의견서에 의할지라도 당초에 금 1천만 원을 한도로 하여         소외 1이나 혹은 원고 (        소외 1이 원고대리인으로 약정한다면)가 피고에게 원본상업자금으로 융차하여 주기로 하여놓고 동 예약금 (금 1천만 원을 말함) 중 금 55만 원 (신화 5,500원) 의 어음으로 우선 융자하여 주고 본건 목적물을 가등기하고 사기한다는 의미로 보아도 계약 당초에 양도매도하였다는 사실을 인정할 수 없다 (4) 을 제3호증의 2의 고소장의 내용을 상세히 관찰한다 할 지라도 예약금 1천만 원을 융자하여줄 계약으로 현시가 500만 원의 본건 목적물을 가등기한 것이지 금 55만 원 (신화 5,500원)에 한하여 가등기한 사실은 아님을 견취할 수 있다 (5) 을 제3호증의 3 증인 전경근 신문조서내용에 의하면 증 제1호증 (각서) 이 「동예약금」 이란 것은 구화 금 1천만 원 대부조건인데         소외 1로부터 기망당한 사실등 피고주장에 부합된 사실이 분명하다 (6) 을 제3호증의 4 청취서내용에 의하면 피고 전경근이가         소외 1을 신임하고 가등기수속만에 사용하라는 조건하에 전경근 인장을         소외 1에게 교부하여 주었더니 (7) 을 제3호증의 5와 같이 증인 사법서사         소외 3에게         소외 1이 독단으로 위탁하여서 (가) 약속수형 (96원야 본건에 갑 제1호증으로 이용) (나) 토지건물매매증서 (본건 갑 제2호증으로 이용)기타 차에 관한 모든 서류를 허위로 작성케 문서를 위조한 사실이 명확하게 되었다 그런데 원심은 차등의 서증을 위조한 것인데도 불구하고 도리어 갑 제1호 갑 제2호증 등으로 진정한 서증으로 제출하여 법원을 오진케하고 기반증에는 을 제3호증의 5를 유탈하여 채증의 법칙에 위배되고 오판이 되었다 (8) 을 제3호증의 6의         소외 1의 청취서에 의하면 전경근이 승낙이 없는데 문서등을 위조한 것 같이 되어있는 것을 암암리에 인정한 것 같이 되어 있다 차증 유탈도 오판의 일원인이 되었다 (9) 을 제3호증의 7         소외 1이 광주지방검찰청에 단기 1953년 9월 일차 출한「소유권 포기서」 의 내용에 의하면 「고소인 전경근의 대서물 등에 관하여 동인의 사전승낙없이 본인 (        소외 1을 운함)이 작성한 약속수형 (96만 원수형을 말함) 및 매매계약서 (갑 제2호증 건물매매계약서를 말함)소유권이전등기신청서 매도증서 등은 현재 귀청에 증거물로 압수되었는바 동 문서등에 관하여는 귀청에서 여하히 처분하여도 이의가 무하옵기 이소유권을 포기함」 한다 라고 본건에 관하여 전권리를 포기하였음에도 불구하고 이서증을 원판결유탈하여 포기된 갑 제1호 제2호증을 증거력의 운명에 관련적으로 제3호증의 7을 방임한 것은 중대한 채증유탈에 심리부진이고 이유불비가 되었다 (10) 을 제3호증의 8 사법서사         소외 3의 청취서내용에 의할지라도 갑 제1호증, 갑 제2호증은 피고 전경근의 의뢰가 없었던 것은 사실이다 이상의 서증을 숙독음미하여 상호고핵하여 본다면 피고주장사실을 인정할 수 있고 또 증거유탈이 되었다는것을 발견함에 충분하므로 원심판결은 차점으로 보아 당연히 취소되여야 한다 운함에 있다        그러나 보통의의의 매도담보계약 또는 양도담보계약이라는것을 일종의 신탁행위로서 채권담보의 목적으로써 담보목적물의 소유권을 채권자에게 이전하며 채권자로 하여금 기담보목적의 범위내에서만 소유권을 행사케하는 채무자 대 채권자간의 담보계약이요 그 효력으로서는 채무자는 채권자로 하여금 일반 제3자에 대한 관계에 있어서 소유자로서 그 권리를 실행시키기 위하여 기목적물이 부동산인때에는 채권자에게 이전등기 절차 및 '부수의무를 이행 하여야하며 채권자는 채무자가 채무를 이행치 않을 때는 해목적물을 시가에 의하여 처분하여 기매득금으로써 피담보채무의 변제에 충당하되 잉여가 있으면 이를 채무자에게 반환하고 부족이 있으면 다시 채무자에게 청구하는 것이다 그런데 일건기록과 원판결에 의하면 원고는 청구취지로서 본건 부동산에 관하여 단기 1950년 3월 1일자 양도담보계약에 인한 소유권이전등기절차이행 및 가옥명도의 판결을 구하고 기청구원인으로서 원고는 단기 1950년 3월 1일 피고에 대하여 기주장의 금원을 대여하고 기담보로서 피고소유인 본건가대에 관하여 즉시로 매매가등기를 받아 두었다가 피고가 기주장의 약정기일에 채무를 이행치 않을 때에는 피고는 본등기절차를 이행함과 동시에 본건가옥을 명도하기로 하는 소위 양도담보계약을 하였다는 취지를 주장하였고 원심은 그의 거시한 증거에 의하여 원고주장의 양도담보계약사실을 인정하여 원고청구를 인용한 사실이 분명하니 당사자간의 특별의사표시가 보이지 않는 본건에 있어서는 서상 원고주장의 양도담보나 이에 의한 원판결 인정의 양도담보는 모두 전설시취지의 보통의 의의 양도담보계약이라고 보아야 할 것이어니와 반대의 견해를 가진 논지는 이유없다 그러나 원판결인정의 양도담보도 역시 채권담보의 성질을 가진 점에 있어서는 소론 보통담보와 동일한 것이라 볼 수 있으므로 원판결인정의 피담보채무가 변제 또는 변제공탁 기타원인에 의하여 소멸된 때에는 그가 본건 이전등기 및 명도의 완료전이면 그 청구권이 당연히 소멸되는 것이오 그의 완료후이요 또 아직 원고의 담보권실행전이면 원고는 적법한 방법으로써 본건가대를 피고에게 반환하여야 할 것이다 그리고 소론 피담보채권에 관하여는 원판결에 의하면 원심은 증거에 의하여 원판시 79만 원 (구하표시 이하동) 의 원리금으로써 본건 피담보채권으로 인정함과 동시에 소론 예약금 1천만 원은 이를 부정한 취지가 분명하니 이에 반한 논지는 이유없고 소론 을 제1, 2 각 호증에 관하여는 기록과 원판결에 의하면 동호증 기재내용을 원판시이유와 같이 해석판정 못할 바 없고 소론을 제3호증의1 내지 8은 원심이 기전권에 의하여 기기재내용의 증거력을 배척한 취지가 원판결이유의 전단말미기재취지에 의하여 규찰되는 바이니 우와 반대의 견해를 가진 논지는 이유없고 소론 갑 제1, 2 각 호증에 의하면 그는 피고에게 불리한 증거임이 그의 기재자체에 비추어 분명한 바 원판결에 의하면 원심은 기직권에 의하여 그 기재내용에 증거력을 취택치 않았으니 원심조치는 결국 피고의 이익에 속한 것이라 볼 수 있음에도 불구하고 차점에 논지는 동호증에 대한 원심조치를 공격함에 있으니 이는 결국 피고의 불이익을 위함에 귀착되는 것으로서 적법한 상고이유라 볼 수 없으므로 채용할 수 없다  이상 이유에 의하여 본건 상고는 기각함이 가하다 인정하고 소송비용부담에 관하여는         민사소송법 제89조제95조를 적용하여 주문과 같이 판결한다      대법관 김두일(재판장) 김동현 배정현 고재호 대리판사 한환진 ";
//
//
//
//        Pattern patternMain = Pattern.compile("[\\[【](\s*주\s*문\s*)[\\]】]");
//
//        Matcher matcherMain = patternMain.matcher(content);
//
//        Pattern patternReason = Pattern.compile("[\\[【](\s*이\s*유\s*)[\\]】]");
//
//        Matcher matcherReason = patternReason.matcher(content);
//
//        if(matcherReason.find() && matcherMain.find()){
//
//            try {
//                String mainStr = content.substring(matcherMain.end(), matcherReason.start() - 1);
//                detailPrecedent.setPrecMain(mainStr);
//            }catch(Exception e){
//                System.out.println("[본문] : 에러");
//            }
//
//            try{
//                String reasonStr = content.substring(matcherReason.end(), content.lastIndexOf(".")+1);
//                detailPrecedent.setPrecReason(reasonStr);
//            }
//            catch (Exception e){
//                System.out.println("[이유] : 에러");
//            }
//
//        }
//
//    }
//}
//
