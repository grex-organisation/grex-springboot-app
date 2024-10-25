package com.grex.service;

import com.grex.configuration.WordConfig;
import com.grex.controller.ProgressController;
import com.grex.dto.FlashCardDto;
import com.grex.dto.GroupDto;
import com.grex.model.Group;
import com.grex.model.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CacheService {

    private final WordConfig wordConfig;
    private Map<String, GroupDto> groupMap = null;
    private List<GroupDto> groupList = null;
    private Map<String, FlashCardDto> FlashCardMap = null;
    private final Map<String, Progress> progressMap = new ConcurrentHashMap<String, Progress>();

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    public CacheService(WordConfig wordConfig) {
        this.wordConfig = wordConfig;
    }

    public Map<String, FlashCardDto> getCachedFlashCardMap() {

        if (FlashCardMap == null || FlashCardMap.isEmpty()) {
            List<Group> groups = wordConfig.getGroups();
            FlashCardMap = new HashMap<>();
            for (Group group : groups) {
                FlashCardMap.put(group.getGroupId(), new FlashCardDto(group.getGroupId(), group.getWords()));
            }
        }
        return FlashCardMap;
    }

    public Map<String, GroupDto> getCachedGroupMap() {

        if (groupMap == null || groupMap.isEmpty()) {
            List<Group> groups = wordConfig.getGroups();
            groupMap = new HashMap<>();
            for (Group group : groups) {
                groupMap.put(group.getGroupId(), new GroupDto(group.getGroupId(), group.getGroupName()));
            }
        }
        return groupMap;
    }

    public List<GroupDto> getCachedGroupList() {

        if (groupList == null || groupList.isEmpty()) {
            List<Group> groups = wordConfig.getGroups();
            groupList = new ArrayList<>();
            for (Group group : groups) {
                groupList.add(new GroupDto(group.getGroupId(), group.getGroupName()));
            }
        }
        return groupList;
    }

    public Map<String, Progress> getProgressMap() {
        return progressMap;
    }

    public void setCachedProgressByGroup(final String stageName, final String column) {

        logger.info("entered setCachedProgressByGroup");

        Progress progress = getProgressMap().get(stageName);

        if (progress == null) {
            logger.warn("No progress found for stage: " + stageName);
            return;
        }

        byte i = 0;

        if (column.equals("g1")) {
            i = (byte) (progress.getG1() + 1);
            progress.setG1(i);  // Set the incremented value back to the Progress object
        } else if (column.equals("g2")) {
            i = (byte) (progress.getG2() + 1);
            progress.setG2(i);
        } else if (column.equals("g3")) {
            i = (byte) (progress.getG3() + 1);
            progress.setG3(i);
        } else if (column.equals("g4")) {
            i = (byte) (progress.getG4() + 1);
            progress.setG4(i);
        } else if (column.equals("g5")) {
            i = (byte) (progress.getG5() + 1);
            progress.setG5(i);
        } else if (column.equals("g6")) {
            i = (byte) (progress.getG6() + 1);
            progress.setG6(i);
        } else if (column.equals("g7")) {
            i = (byte) (progress.getG7() + 1);
            progress.setG7(i);
        } else if (column.equals("g8")) {
            i = (byte) (progress.getG8() + 1);
            progress.setG8(i);
        } else if (column.equals("g9")) {
            i = (byte) (progress.getG9() + 1);
            progress.setG9(i);
        } else if (column.equals("g10")) {
            i = (byte) (progress.getG10() + 1);
            progress.setG10(i);
        } else if (column.equals("g11")) {
            i = (byte) (progress.getG11() + 1);
            progress.setG11(i);
        } else if (column.equals("g12")) {
            i = (byte) (progress.getG12() + 1);
            progress.setG12(i);
        } else if (column.equals("g13")) {
            i = (byte) (progress.getG13() + 1);
            progress.setG13(i);
        } else if (column.equals("g14")) {
            i = (byte) (progress.getG14() + 1);
            progress.setG14(i);
        } else if (column.equals("g15")) {
            i = (byte) (progress.getG15() + 1);
            progress.setG15(i);
        } else if (column.equals("g16")) {
            i = (byte) (progress.getG16() + 1);
            progress.setG16(i);
        } else if (column.equals("g17")) {
            i = (byte) (progress.getG17() + 1);
            progress.setG17(i);
        } else if (column.equals("g18")) {
            i = (byte) (progress.getG18() + 1);
            progress.setG18(i);
        } else if (column.equals("g19")) {
            i = (byte) (progress.getG19() + 1);
            progress.setG19(i);
        } else if (column.equals("g20")) {
            i = (byte) (progress.getG20() + 1);
            progress.setG20(i);
        } else if (column.equals("g21")) {
            i = (byte) (progress.getG21() + 1);
            progress.setG21(i);
        } else if (column.equals("g22")) {
            i = (byte) (progress.getG22() + 1);
            progress.setG22(i);
        } else if (column.equals("g23")) {
            i = (byte) (progress.getG23() + 1);
            progress.setG23(i);
        } else if (column.equals("g24")) {
            i = (byte) (progress.getG24() + 1);
            progress.setG24(i);
        } else if (column.equals("g25")) {
            i = (byte) (progress.getG25() + 1);
            progress.setG25(i);
        } else if (column.equals("g26")) {
            i = (byte) (progress.getG26() + 1);
            progress.setG26(i);
        } else if (column.equals("g27")) {
            i = (byte) (progress.getG27() + 1);
            progress.setG27(i);
        } else if (column.equals("g28")) {
            i = (byte) (progress.getG28() + 1);
            progress.setG28(i);
        } else if (column.equals("g29")) {
            i = (byte) (progress.getG29() + 1);
            progress.setG29(i);
        } else if (column.equals("g30")) {
            i = (byte) (progress.getG30() + 1);
            progress.setG30(i);
        } else if (column.equals("g31")) {
            i = (byte) (progress.getG31() + 1);
            progress.setG31(i);
        } else if (column.equals("g32")) {
            i = (byte) (progress.getG32() + 1);
            progress.setG32(i);
        } else if (column.equals("g33")) {
            i = (byte) (progress.getG33() + 1);
            progress.setG33(i);
        } else if (column.equals("g34")) {
            i = (byte) (progress.getG34() + 1);
            progress.setG34(i);
        } else if (column.equals("g35")) {
            i = (byte) (progress.getG35() + 1);
            progress.setG35(i);
        } else if (column.equals("g36")) {
            i = (byte) (progress.getG36() + 1);
            progress.setG36(i);
        } else if (column.equals("g37")) {
            i = (byte) (progress.getG37() + 1);
            progress.setG37(i);
        } else if (column.equals("g38")) {
            i = (byte) (progress.getG38() + 1);
            progress.setG38(i);
        } else if (column.equals("g39")) {
            i = (byte) (progress.getG39() + 1);
            progress.setG39(i);
        } else if (column.equals("g40")) {
            i = (byte) (progress.getG40() + 1);
            progress.setG40(i);
        } else if (column.equals("g41")) {
            i = (byte) (progress.getG41() + 1);
            progress.setG41(i);
        } else if (column.equals("g42")) {
            i = (byte) (progress.getG42() + 1);
            progress.setG42(i);
        } else if (column.equals("g43")) {
            i = (byte) (progress.getG43() + 1);
            progress.setG43(i);
        } else if (column.equals("g44")) {
            i = (byte) (progress.getG44() + 1);
            progress.setG44(i);
        } else if (column.equals("g45")) {
            i = (byte) (progress.getG45() + 1);
            progress.setG45(i);
        } else if (column.equals("g46")) {
            i = (byte) (progress.getG46() + 1);
            progress.setG46(i);
        } else if (column.equals("g47")) {
            i = (byte) (progress.getG47() + 1);
            progress.setG47(i);
        } else if (column.equals("g48")) {
            i = (byte) (progress.getG48() + 1);
            progress.setG48(i);
        } else if (column.equals("g49")) {
            i = (byte) (progress.getG49() + 1);
            progress.setG49(i);
        } else if (column.equals("g50")) {
            i = (byte) (progress.getG50() + 1);
            progress.setG50(i);
        } else if (column.equals("g51")) {
            i = (byte) (progress.getG51() + 1);
            progress.setG51(i);
        } else if (column.equals("g52")) {
            i = (byte) (progress.getG52() + 1);
            progress.setG52(i);
        } else if (column.equals("g53")) {
            i = (byte) (progress.getG53() + 1);
            progress.setG53(i);
        } else if (column.equals("g54")) {
            i = (byte) (progress.getG54() + 1);
            progress.setG54(i);
        } else if (column.equals("g55")) {
            i = (byte) (progress.getG55() + 1);
            progress.setG55(i);
        } else if (column.equals("g56")) {
            i = (byte) (progress.getG56() + 1);
            progress.setG56(i);
        } else if (column.equals("g57")) {
            i = (byte) (progress.getG57() + 1);
            progress.setG57(i);
        } else if (column.equals("g58")) {
            i = (byte) (progress.getG58() + 1);
            progress.setG58(i);
        } else if (column.equals("g59")) {
            i = (byte) (progress.getG59() + 1);
            progress.setG59(i);
        } else if (column.equals("g60")) {
            i = (byte) (progress.getG60() + 1);
            progress.setG60(i);
        } else if (column.equals("g61")) {
            i = (byte) (progress.getG61() + 1);
            progress.setG61(i);
        } else if (column.equals("g62")) {
            i = (byte) (progress.getG62() + 1);
            progress.setG62(i);
        } else if (column.equals("g63")) {
            i = (byte) (progress.getG63() + 1);
            progress.setG63(i);
        } else if (column.equals("g64")) {
            i = (byte) (progress.getG64() + 1);
            progress.setG64(i);
        } else if (column.equals("g65")) {
            i = (byte) (progress.getG65() + 1);
            progress.setG65(i);
        } else if (column.equals("g66")) {
            i = (byte) (progress.getG66() + 1);
            progress.setG66(i);
        } else if (column.equals("g67")) {
            i = (byte) (progress.getG67() + 1);
            progress.setG67(i);
        } else if (column.equals("g68")) {
            i = (byte) (progress.getG68() + 1);
            progress.setG68(i);
        } else if (column.equals("g69")) {
            i = (byte) (progress.getG69() + 1);
            progress.setG69(i);
        } else if (column.equals("g70")) {
            i = (byte) (progress.getG70() + 1);
            progress.setG70(i);
        } else if (column.equals("g71")) {
            i = (byte) (progress.getG71() + 1);
            progress.setG71(i);
        } else if (column.equals("g72")) {
            i = (byte) (progress.getG72() + 1);
            progress.setG72(i);
        } else if (column.equals("g73")) {
            i = (byte) (progress.getG73() + 1);
            progress.setG73(i);
        } else if (column.equals("g74")) {
            i = (byte) (progress.getG74() + 1);
            progress.setG74(i);
        } else if (column.equals("g75")) {
            i = (byte) (progress.getG75() + 1);
            progress.setG75(i);
        } else if (column.equals("g76")) {
            i = (byte) (progress.getG76() + 1);
            progress.setG76(i);
        } else if (column.equals("g77")) {
            i = (byte) (progress.getG77() + 1);
            progress.setG77(i);
        } else if (column.equals("g78")) {
            i = (byte) (progress.getG78() + 1);
            progress.setG78(i);
        } else if (column.equals("g79")) {
            i = (byte) (progress.getG79() + 1);
            progress.setG79(i);
        } else if (column.equals("g80")) {
            i = (byte) (progress.getG80() + 1);
            progress.setG80(i);
        } else if (column.equals("g81")) {
            i = (byte) (progress.getG81() + 1);
            progress.setG81(i);
        } else if (column.equals("g82")) {
            i = (byte) (progress.getG82() + 1);
            progress.setG82(i);
        } else if (column.equals("g83")) {
            i = (byte) (progress.getG83() + 1);
            progress.setG83(i);
        } else if (column.equals("g84")) {
            i = (byte) (progress.getG84() + 1);
            progress.setG84(i);
        } else if (column.equals("g85")) {
            i = (byte) (progress.getG85() + 1);
            progress.setG85(i);
        } else if (column.equals("g86")) {
            i = (byte) (progress.getG86() + 1);
            progress.setG86(i);
        } else if (column.equals("g87")) {
            i = (byte) (progress.getG87() + 1);
            progress.setG87(i);
        } else if (column.equals("g88")) {
            i = (byte) (progress.getG88() + 1);
            progress.setG88(i);
        } else if (column.equals("g89")) {
            i = (byte) (progress.getG89() + 1);
            progress.setG89(i);
        } else if (column.equals("g90")) {
            i = (byte) (progress.getG90() + 1);
            progress.setG90(i);
        } else if (column.equals("g91")) {
            i = (byte) (progress.getG91() + 1);
            progress.setG91(i);
        } else if (column.equals("g92")) {
            i = (byte) (progress.getG92() + 1);
            progress.setG92(i);
        } else if (column.equals("g93")) {
            i = (byte) (progress.getG93() + 1);
            progress.setG93(i);
        } else if (column.equals("g94")) {
            i = (byte) (progress.getG94() + 1);
            progress.setG94(i);
        } else if (column.equals("g95")) {
            i = (byte) (progress.getG95() + 1);
            progress.setG95(i);
        } else if (column.equals("g96")) {
            i = (byte) (progress.getG96() + 1);
            progress.setG96(i);
        } else if (column.equals("g97")) {
            i = (byte) (progress.getG97() + 1);
            progress.setG97(i);
        } else if (column.equals("g98")) {
            i = (byte) (progress.getG98() + 1);
            progress.setG98(i);
        } else if (column.equals("g99")) {
            i = (byte) (progress.getG99() + 1);
            progress.setG99(i);
        } else if (column.equals("g100")) {
            i = (byte) (progress.getG100() + 1);
            progress.setG100(i);
        } else if (column.equals("g101")) {
            i = (byte) (progress.getG101() + 1);
            progress.setG101(i);
        } else if (column.equals("g102")) {
            i = (byte) (progress.getG102() + 1);
            progress.setG102(i);
        } else if (column.equals("g103")) {
            i = (byte) (progress.getG103() + 1);
            progress.setG103(i);
        } else if (column.equals("g104")) {
            i = (byte) (progress.getG104() + 1);
            progress.setG104(i);
        } else if (column.equals("g105")) {
            i = (byte) (progress.getG105() + 1);
            progress.setG105(i);
        } else if (column.equals("g106")) {
            i = (byte) (progress.getG106() + 1);
            progress.setG106(i);
        } else if (column.equals("g107")) {
            i = (byte) (progress.getG107() + 1);
            progress.setG107(i);
        } else if (column.equals("g108")) {
            i = (byte) (progress.getG108() + 1);
            progress.setG108(i);
        } else if (column.equals("g109")) {
            i = (byte) (progress.getG109() + 1);
            progress.setG109(i);
        } else if (column.equals("g110")) {
            i = (byte) (progress.getG110() + 1);
            progress.setG110(i);
        } else if (column.equals("g111")) {
            i = (byte) (progress.getG111() + 1);
            progress.setG111(i);
        } else if (column.equals("g112")) {
            i = (byte) (progress.getG112() + 1);
            progress.setG112(i);
        } else if (column.equals("g113")) {
            i = (byte) (progress.getG113() + 1);
            progress.setG113(i);
        } else if (column.equals("g114")) {
            i = (byte) (progress.getG114() + 1);
            progress.setG114(i);
        } else if (column.equals("g115")) {
            i = (byte) (progress.getG115() + 1);
            progress.setG115(i);
        } else if (column.equals("g116")) {
            i = (byte) (progress.getG116() + 1);
            progress.setG116(i);
        } else if (column.equals("g117")) {
            i = (byte) (progress.getG117() + 1);
            progress.setG117(i);
        } else if (column.equals("g118")) {
            i = (byte) (progress.getG118() + 1);
            progress.setG118(i);
        } else if (column.equals("g119")) {
            i = (byte) (progress.getG119() + 1);
            progress.setG119(i);
        } else if (column.equals("g120")) {
            i = (byte) (progress.getG120() + 1);
            progress.setG120(i);
        } else if (column.equals("g121")) {
            i = (byte) (progress.getG121() + 1);
            progress.setG121(i);
        } else if (column.equals("g122")) {
            i = (byte) (progress.getG122() + 1);
            progress.setG122(i);
        } else if (column.equals("g123")) {
            i = (byte) (progress.getG123() + 1);
            progress.setG123(i);
        } else if (column.equals("g124")) {
            i = (byte) (progress.getG124() + 1);
            progress.setG124(i);
        } else if (column.equals("g125")) {
            i = (byte) (progress.getG125() + 1);
            progress.setG125(i);
        } else if (column.equals("g126")) {
            i = (byte) (progress.getG126() + 1);
            progress.setG126(i);
        } else if (column.equals("g127")) {
            i = (byte) (progress.getG127() + 1);
            progress.setG127(i);
        } else if (column.equals("g128")) {
            i = (byte) (progress.getG128() + 1);
            progress.setG128(i);
        } else if (column.equals("g129")) {
            i = (byte) (progress.getG129() + 1);
            progress.setG129(i);
        } else if (column.equals("g130")) {
            i = (byte) (progress.getG130() + 1);
            progress.setG130(i);
        } else if (column.equals("g131")) {
            i = (byte) (progress.getG131() + 1);
            progress.setG131(i);
        } else if (column.equals("g132")) {
            i = (byte) (progress.getG132() + 1);
            progress.setG132(i);
        } else if (column.equals("g133")) {
            i = (byte) (progress.getG133() + 1);
            progress.setG133(i);
        } else if (column.equals("g134")) {
            i = (byte) (progress.getG134() + 1);
            progress.setG134(i);
        } else if (column.equals("g135")) {
            i = (byte) (progress.getG135() + 1);
            progress.setG135(i);
        } else if (column.equals("g136")) {
            i = (byte) (progress.getG136() + 1);
            progress.setG136(i);
        } else if (column.equals("g137")) {
            i = (byte) (progress.getG137() + 1);
            progress.setG137(i);
        } else if (column.equals("g138")) {
            i = (byte) (progress.getG138() + 1);
            progress.setG138(i);
        } else if (column.equals("g139")) {
            i = (byte) (progress.getG139() + 1);
            progress.setG139(i);
        } else if (column.equals("g140")) {
            i = (byte) (progress.getG140() + 1);
            progress.setG140(i);
        } else if (column.equals("g141")) {
            i = (byte) (progress.getG141() + 1);
            progress.setG141(i);
        } else if (column.equals("g142")) {
            i = (byte) (progress.getG142() + 1);
            progress.setG142(i);
        } else if (column.equals("g143")) {
            i = (byte) (progress.getG143() + 1);
            progress.setG143(i);
        } else if (column.equals("g144")) {
            i = (byte) (progress.getG144() + 1);
            progress.setG144(i);
        } else if (column.equals("g145")) {
            i = (byte) (progress.getG145() + 1);
            progress.setG145(i);
        } else if (column.equals("g146")) {
            i = (byte) (progress.getG146() + 1);
            progress.setG146(i);
        } else if (column.equals("g147")) {
            i = (byte) (progress.getG147() + 1);
            progress.setG147(i);
        } else if (column.equals("g148")) {
            i = (byte) (progress.getG148() + 1);
            progress.setG148(i);
        } else if (column.equals("g149")) {
            i = (byte) (progress.getG149() + 1);
            progress.setG149(i);
        } else if (column.equals("g150")) {
            i = (byte) (progress.getG150() + 1);
            progress.setG150(i);
        } else if (column.equals("g151")) {
            i = (byte) (progress.getG151() + 1);
            progress.setG151(i);
        } else if (column.equals("g152")) {
            i = (byte) (progress.getG152() + 1);
            progress.setG152(i);
        } else if (column.equals("g153")) {
            i = (byte) (progress.getG153() + 1);
            progress.setG153(i);
        } else if (column.equals("g154")) {
            i = (byte) (progress.getG154() + 1);
            progress.setG154(i);
        } else if (column.equals("g155")) {
            i = (byte) (progress.getG155() + 1);
            progress.setG155(i);
        } else if (column.equals("g156")) {
            i = (byte) (progress.getG156() + 1);
            progress.setG156(i);
        } else if (column.equals("g157")) {
            i = (byte) (progress.getG157() + 1);
            progress.setG157(i);
        } else if (column.equals("g158")) {
            i = (byte) (progress.getG158() + 1);
            progress.setG158(i);
        } else if (column.equals("g159")) {
            i = (byte) (progress.getG159() + 1);
            progress.setG159(i);
        } else if (column.equals("g160")) {
            i = (byte) (progress.getG160() + 1);
            progress.setG160(i);
        } else if (column.equals("g161")) {
            i = (byte) (progress.getG161() + 1);
            progress.setG161(i);
        } else if (column.equals("g162")) {
            i = (byte) (progress.getG162() + 1);
            progress.setG162(i);
        } else if (column.equals("g163")) {
            i = (byte) (progress.getG163() + 1);
            progress.setG163(i);
        } else if (column.equals("g164")) {
            i = (byte) (progress.getG164() + 1);
            progress.setG164(i);
        } else if (column.equals("g165")) {
            i = (byte) (progress.getG165() + 1);
            progress.setG165(i);
        } else if (column.equals("g166")) {
            i = (byte) (progress.getG166() + 1);
            progress.setG166(i);
        } else if (column.equals("g167")) {
            i = (byte) (progress.getG167() + 1);
            progress.setG167(i);
        } else if (column.equals("g168")) {
            i = (byte) (progress.getG168() + 1);
            progress.setG168(i);
        } else if (column.equals("g169")) {
            i = (byte) (progress.getG169() + 1);
            progress.setG169(i);
        } else if (column.equals("g170")) {
            i = (byte) (progress.getG170() + 1);
            progress.setG170(i);
        } else if (column.equals("g171")) {
            i = (byte) (progress.getG171() + 1);
            progress.setG171(i);
        } else if (column.equals("g172")) {
            i = (byte) (progress.getG172() + 1);
            progress.setG172(i);
        } else if (column.equals("g173")) {
            i = (byte) (progress.getG173() + 1);
            progress.setG173(i);
        } else if (column.equals("g174")) {
            i = (byte) (progress.getG174() + 1);
            progress.setG174(i);
        } else if (column.equals("g175")) {
            i = (byte) (progress.getG175() + 1);
            progress.setG175(i);
        } else if (column.equals("g176")) {
            i = (byte) (progress.getG176() + 1);
            progress.setG176(i);
        } else if (column.equals("g177")) {
            i = (byte) (progress.getG177() + 1);
            progress.setG177(i);
        } else if (column.equals("g178")) {
            i = (byte) (progress.getG178() + 1);
            progress.setG178(i);
        } else if (column.equals("g179")) {
            i = (byte) (progress.getG179() + 1);
            progress.setG179(i);
        } else if (column.equals("g180")) {
            i = (byte) (progress.getG180() + 1);
            progress.setG180(i);
        } else if (column.equals("g181")) {
            i = (byte) (progress.getG181() + 1);
            progress.setG181(i);
        } else if (column.equals("g182")) {
            i = (byte) (progress.getG182() + 1);
            progress.setG182(i);
        } else if (column.equals("g183")) {
            i = (byte) (progress.getG183() + 1);
            progress.setG183(i);
        } else if (column.equals("g184")) {
            i = (byte) (progress.getG184() + 1);
            progress.setG184(i);
        } else if (column.equals("g185")) {
            i = (byte) (progress.getG185() + 1);
            progress.setG185(i);
        } else if (column.equals("g186")) {
            i = (byte) (progress.getG186() + 1);
            progress.setG186(i);
        } else if (column.equals("g187")) {
            i = (byte) (progress.getG187() + 1);
            progress.setG187(i);
        } else if (column.equals("g188")) {
            i = (byte) (progress.getG188() + 1);
            progress.setG188(i);
        } else if (column.equals("g189")) {
            i = (byte) (progress.getG189() + 1);
            progress.setG189(i);
        } else if (column.equals("g190")) {
            i = (byte) (progress.getG190() + 1);
            progress.setG190(i);
        } else if (column.equals("g191")) {
            i = (byte) (progress.getG191() + 1);
            progress.setG191(i);
        } else if (column.equals("g192")) {
            i = (byte) (progress.getG192() + 1);
            progress.setG192(i);
        } else if (column.equals("g193")) {
            i = (byte) (progress.getG193() + 1);
            progress.setG193(i);
        } else if (column.equals("g194")) {
            i = (byte) (progress.getG194() + 1);
            progress.setG194(i);
        } else if (column.equals("g195")) {
            i = (byte) (progress.getG195() + 1);
            progress.setG195(i);
        } else if (column.equals("g196")) {
            i = (byte) (progress.getG196() + 1);
            progress.setG196(i);
        } else if (column.equals("g197")) {
            i = (byte) (progress.getG197() + 1);
            progress.setG197(i);
        } else if (column.equals("g198")) {
            i = (byte) (progress.getG198() + 1);
            progress.setG198(i);
        } else if (column.equals("g199")) {
            i = (byte) (progress.getG199() + 1);
            progress.setG199(i);
        } else if (column.equals("g200")) {
            i = (byte) (progress.getG200() + 1);
            progress.setG200(i);
        }
    }

    public byte getCachedProgressByGroup(final String stageName, final String column) {

        logger.info("entered getCachedProgressByGroup");

        Progress progress = getProgressMap().get(stageName);

        if (column.equals("g1")) {
            return progress.getG1();
        } else if (column.equals("g2")) {
            return progress.getG2();
        } else if (column.equals("g3")) {
            return progress.getG3();
        } else if (column.equals("g4")) {
            return progress.getG4();
        } else if (column.equals("g5")) {
            return progress.getG5();
        } else if (column.equals("g6")) {
            return progress.getG6();
        } else if (column.equals("g7")) {
            return progress.getG7();
        } else if (column.equals("g8")) {
            return progress.getG8();
        } else if (column.equals("g9")) {
            return progress.getG9();
        } else if (column.equals("g10")) {
            return progress.getG10();
        } else if (column.equals("g11")) {
            return progress.getG11();
        } else if (column.equals("g12")) {
            return progress.getG12();
        } else if (column.equals("g13")) {
            return progress.getG13();
        } else if (column.equals("g14")) {
            return progress.getG14();
        } else if (column.equals("g15")) {
            return progress.getG15();
        } else if (column.equals("g16")) {
            return progress.getG16();
        } else if (column.equals("g17")) {
            return progress.getG17();
        } else if (column.equals("g18")) {
            return progress.getG18();
        } else if (column.equals("g19")) {
            return progress.getG19();
        } else if (column.equals("g20")) {
            return progress.getG20();
        } else if (column.equals("g21")) {
            return progress.getG21();
        } else if (column.equals("g22")) {
            return progress.getG22();
        } else if (column.equals("g23")) {
            return progress.getG23();
        } else if (column.equals("g24")) {
            return progress.getG24();
        } else if (column.equals("g25")) {
            return progress.getG25();
        } else if (column.equals("g26")) {
            return progress.getG26();
        } else if (column.equals("g27")) {
            return progress.getG27();
        } else if (column.equals("g28")) {
            return progress.getG28();
        } else if (column.equals("g29")) {
            return progress.getG29();
        } else if (column.equals("g30")) {
            return progress.getG30();
        } else if (column.equals("g31")) {
            return progress.getG31();
        } else if (column.equals("g32")) {
            return progress.getG32();
        } else if (column.equals("g33")) {
            return progress.getG33();
        } else if (column.equals("g34")) {
            return progress.getG34();
        } else if (column.equals("g35")) {
            return progress.getG35();
        } else if (column.equals("g36")) {
            return progress.getG36();
        } else if (column.equals("g37")) {
            return progress.getG37();
        } else if (column.equals("g38")) {
            return progress.getG38();
        } else if (column.equals("g39")) {
            return progress.getG39();
        } else if (column.equals("g40")) {
            return progress.getG40();
        } else if (column.equals("g41")) {
            return progress.getG41();
        } else if (column.equals("g42")) {
            return progress.getG42();
        } else if (column.equals("g43")) {
            return progress.getG43();
        } else if (column.equals("g44")) {
            return progress.getG44();
        } else if (column.equals("g45")) {
            return progress.getG45();
        } else if (column.equals("g46")) {
            return progress.getG46();
        } else if (column.equals("g47")) {
            return progress.getG47();
        } else if (column.equals("g48")) {
            return progress.getG48();
        } else if (column.equals("g49")) {
            return progress.getG49();
        } else if (column.equals("g50")) {
            return progress.getG50();
        } else if (column.equals("g51")) {
            return progress.getG51();
        } else if (column.equals("g52")) {
            return progress.getG52();
        } else if (column.equals("g53")) {
            return progress.getG53();
        } else if (column.equals("g54")) {
            return progress.getG54();
        } else if (column.equals("g55")) {
            return progress.getG55();
        } else if (column.equals("g56")) {
            return progress.getG56();
        } else if (column.equals("g57")) {
            return progress.getG57();
        } else if (column.equals("g58")) {
            return progress.getG58();
        } else if (column.equals("g59")) {
            return progress.getG59();
        } else if (column.equals("g60")) {
            return progress.getG60();
        } else if (column.equals("g61")) {
            return progress.getG61();
        } else if (column.equals("g62")) {
            return progress.getG62();
        } else if (column.equals("g63")) {
            return progress.getG63();
        } else if (column.equals("g64")) {
            return progress.getG64();
        } else if (column.equals("g65")) {
            return progress.getG65();
        } else if (column.equals("g66")) {
            return progress.getG66();
        } else if (column.equals("g67")) {
            return progress.getG67();
        } else if (column.equals("g68")) {
            return progress.getG68();
        } else if (column.equals("g69")) {
            return progress.getG69();
        } else if (column.equals("g70")) {
            return progress.getG70();
        } else if (column.equals("g71")) {
            return progress.getG71();
        } else if (column.equals("g72")) {
            return progress.getG72();
        } else if (column.equals("g73")) {
            return progress.getG73();
        } else if (column.equals("g74")) {
            return progress.getG74();
        } else if (column.equals("g75")) {
            return progress.getG75();
        } else if (column.equals("g76")) {
            return progress.getG76();
        } else if (column.equals("g77")) {
            return progress.getG77();
        } else if (column.equals("g78")) {
            return progress.getG78();
        } else if (column.equals("g79")) {
            return progress.getG79();
        } else if (column.equals("g80")) {
            return progress.getG80();
        } else if (column.equals("g81")) {
            return progress.getG81();
        } else if (column.equals("g82")) {
            return progress.getG82();
        } else if (column.equals("g83")) {
            return progress.getG83();
        } else if (column.equals("g84")) {
            return progress.getG84();
        } else if (column.equals("g85")) {
            return progress.getG85();
        } else if (column.equals("g86")) {
            return progress.getG86();
        } else if (column.equals("g87")) {
            return progress.getG87();
        } else if (column.equals("g88")) {
            return progress.getG88();
        } else if (column.equals("g89")) {
            return progress.getG89();
        } else if (column.equals("g90")) {
            return progress.getG90();
        } else if (column.equals("g91")) {
            return progress.getG91();
        } else if (column.equals("g92")) {
            return progress.getG92();
        } else if (column.equals("g93")) {
            return progress.getG93();
        } else if (column.equals("g94")) {
            return progress.getG94();
        } else if (column.equals("g95")) {
            return progress.getG95();
        } else if (column.equals("g96")) {
            return progress.getG96();
        } else if (column.equals("g97")) {
            return progress.getG97();
        } else if (column.equals("g98")) {
            return progress.getG98();
        } else if (column.equals("g99")) {
            return progress.getG99();
        } else if (column.equals("g100")) {
            return progress.getG100();
        } else if (column.equals("g101")) {
            return progress.getG101();
        } else if (column.equals("g102")) {
            return progress.getG102();
        } else if (column.equals("g103")) {
            return progress.getG103();
        } else if (column.equals("g104")) {
            return progress.getG104();
        } else if (column.equals("g105")) {
            return progress.getG105();
        } else if (column.equals("g106")) {
            return progress.getG106();
        } else if (column.equals("g107")) {
            return progress.getG107();
        } else if (column.equals("g108")) {
            return progress.getG108();
        } else if (column.equals("g109")) {
            return progress.getG109();
        } else if (column.equals("g110")) {
            return progress.getG110();
        } else if (column.equals("g111")) {
            return progress.getG111();
        } else if (column.equals("g112")) {
            return progress.getG112();
        } else if (column.equals("g113")) {
            return progress.getG113();
        } else if (column.equals("g114")) {
            return progress.getG114();
        } else if (column.equals("g115")) {
            return progress.getG115();
        } else if (column.equals("g116")) {
            return progress.getG116();
        } else if (column.equals("g117")) {
            return progress.getG117();
        } else if (column.equals("g118")) {
            return progress.getG118();
        } else if (column.equals("g119")) {
            return progress.getG119();
        } else if (column.equals("g120")) {
            return progress.getG120();
        } else if (column.equals("g121")) {
            return progress.getG121();
        } else if (column.equals("g122")) {
            return progress.getG122();
        } else if (column.equals("g123")) {
            return progress.getG123();
        } else if (column.equals("g124")) {
            return progress.getG124();
        } else if (column.equals("g125")) {
            return progress.getG125();
        } else if (column.equals("g126")) {
            return progress.getG126();
        } else if (column.equals("g127")) {
            return progress.getG127();
        } else if (column.equals("g128")) {
            return progress.getG128();
        } else if (column.equals("g129")) {
            return progress.getG129();
        } else if (column.equals("g130")) {
            return progress.getG130();
        } else if (column.equals("g131")) {
            return progress.getG131();
        } else if (column.equals("g132")) {
            return progress.getG132();
        } else if (column.equals("g133")) {
            return progress.getG133();
        } else if (column.equals("g134")) {
            return progress.getG134();
        } else if (column.equals("g135")) {
            return progress.getG135();
        } else if (column.equals("g136")) {
            return progress.getG136();
        } else if (column.equals("g137")) {
            return progress.getG137();
        } else if (column.equals("g138")) {
            return progress.getG138();
        } else if (column.equals("g139")) {
            return progress.getG139();
        } else if (column.equals("g140")) {
            return progress.getG140();
        } else if (column.equals("g141")) {
            return progress.getG141();
        } else if (column.equals("g142")) {
            return progress.getG142();
        } else if (column.equals("g143")) {
            return progress.getG143();
        } else if (column.equals("g144")) {
            return progress.getG144();
        } else if (column.equals("g145")) {
            return progress.getG145();
        } else if (column.equals("g146")) {
            return progress.getG146();
        } else if (column.equals("g147")) {
            return progress.getG147();
        } else if (column.equals("g148")) {
            return progress.getG148();
        } else if (column.equals("g149")) {
            return progress.getG149();
        } else if (column.equals("g150")) {
            return progress.getG150();
        } else if (column.equals("g151")) {
            return progress.getG151();
        } else if (column.equals("g152")) {
            return progress.getG152();
        } else if (column.equals("g153")) {
            return progress.getG153();
        } else if (column.equals("g154")) {
            return progress.getG154();
        } else if (column.equals("g155")) {
            return progress.getG155();
        } else if (column.equals("g156")) {
            return progress.getG156();
        } else if (column.equals("g157")) {
            return progress.getG157();
        } else if (column.equals("g158")) {
            return progress.getG158();
        } else if (column.equals("g159")) {
            return progress.getG159();
        } else if (column.equals("g160")) {
            return progress.getG160();
        } else if (column.equals("g161")) {
            return progress.getG161();
        } else if (column.equals("g162")) {
            return progress.getG162();
        } else if (column.equals("g163")) {
            return progress.getG163();
        } else if (column.equals("g164")) {
            return progress.getG164();
        } else if (column.equals("g165")) {
            return progress.getG165();
        } else if (column.equals("g166")) {
            return progress.getG166();
        } else if (column.equals("g167")) {
            return progress.getG167();
        } else if (column.equals("g168")) {
            return progress.getG168();
        } else if (column.equals("g169")) {
            return progress.getG169();
        } else if (column.equals("g170")) {
            return progress.getG170();
        } else if (column.equals("g171")) {
            return progress.getG171();
        } else if (column.equals("g172")) {
            return progress.getG172();
        } else if (column.equals("g173")) {
            return progress.getG173();
        } else if (column.equals("g174")) {
            return progress.getG174();
        } else if (column.equals("g175")) {
            return progress.getG175();
        } else if (column.equals("g176")) {
            return progress.getG176();
        } else if (column.equals("g177")) {
            return progress.getG177();
        } else if (column.equals("g178")) {
            return progress.getG178();
        } else if (column.equals("g179")) {
            return progress.getG179();
        } else if (column.equals("g180")) {
            return progress.getG180();
        } else if (column.equals("g181")) {
            return progress.getG181();
        } else if (column.equals("g182")) {
            return progress.getG182();
        } else if (column.equals("g183")) {
            return progress.getG183();
        } else if (column.equals("g184")) {
            return progress.getG184();
        } else if (column.equals("g185")) {
            return progress.getG185();
        } else if (column.equals("g186")) {
            return progress.getG186();
        } else if (column.equals("g187")) {
            return progress.getG187();
        } else if (column.equals("g188")) {
            return progress.getG188();
        } else if (column.equals("g189")) {
            return progress.getG189();
        } else if (column.equals("g190")) {
            return progress.getG190();
        } else if (column.equals("g191")) {
            return progress.getG191();
        } else if (column.equals("g192")) {
            return progress.getG192();
        } else if (column.equals("g193")) {
            return progress.getG193();
        } else if (column.equals("g194")) {
            return progress.getG194();
        } else if (column.equals("g195")) {
            return progress.getG195();
        } else if (column.equals("g196")) {
            return progress.getG196();
        } else if (column.equals("g197")) {
            return progress.getG197();
        } else if (column.equals("g198")) {
            return progress.getG198();
        } else if (column.equals("g199")) {
            return progress.getG199();
        } else if (column.equals("g200")) {
            return progress.getG200();
        }

        return 0;
    }

}
