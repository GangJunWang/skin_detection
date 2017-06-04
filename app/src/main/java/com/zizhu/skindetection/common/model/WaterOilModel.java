package com.zizhu.skindetection.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhongxin on 2016/11/26.
 */
public class WaterOilModel {

    public String order;
    public String type;
    public static List<WaterOilModel> waterOilModels = new ArrayList<WaterOilModel>();
    public List<WaterOilResultModel> waterOilResultModels = new ArrayList<WaterOilResultModel>();

    static {
        WaterOilModel waterOilModel1 = addWaterOilModel("T", "水分正常/油分正常");
        waterOilModel1.waterOilResultModels.add(addWaterOilResultModel("水油平衡", "脸颊油分和水分都恰到好处，最为完美的肌肤状态。目前测试阶段，暂无潜在皮肤问题隐患。" +
                "水油平衡的肌肤也会受季节影响，夏天趋向油性要去油，冬天趋向干性需补水，在换季的时候，要比平时多一分呵护，季节性地选择不同的护肤品。", ""));
        WaterOilModel waterOilModel2 = addWaterOilModel("T", "十分水润/油分正常");
        waterOilModel2.waterOilResultModels.add(addWaterOilResultModel("水油平衡", "脸颊油分和水分都恰到好处，最为完美的肌肤状态。目前测试阶段，暂无潜在皮肤问题隐患。" +
                "水油平衡的肌肤也会受季节影响，夏天趋向油性要去油，冬天趋向干性需补水，在换季的时候，要比平时多一分呵护，季节性地选择不同的护肤品。", ""));
        WaterOilModel waterOilModel3 = addWaterOilModel("T", "水分正常/缺油");
        waterOilModel3.waterOilResultModels.add(addWaterOilResultModel("肌肤屏障薄弱", "肌肤屏障薄弱是因为肌肤油脂分泌量不足，肌肤最外层是天然保护膜不够厚实，无法抵挡外界刺激物的伤害。",
                "肌肤屏障薄弱需要提高肌肤免疫力。建议使用含有角鲨烯、神经酰胺、荷荷芭酯等油脂成分的护肤品。作息规律、饮食合理。还有不能乱用含香料色素、矿物油及动物成分化妆品。"));
        waterOilModel3.waterOilResultModels.add(addWaterOilResultModel("红血丝", "皮肤缺水又缺油，最外层角质薄弱，皮肤薄而敏感，更容易受到外界刺激。",
                "红血丝的皮肤一般都是比较敏感的，所以不能用一些带有激素的产品。比如美白方面的产品激素比较重，脸上有红血丝避免使用。建议为肌肤选择含有角鲨烯、神经酰胺、荷荷芭酯等油脂成分的护肤品，加固肌肤屏障。"));
        waterOilModel3.waterOilResultModels.add(addWaterOilResultModel("肌肤缺水缺油", "肌肤的皮脂腺先天不足，不能分泌足够肌肤所需的油脂，肌肤没有锁水能力，如果只靠单纯补水，容易造成“越补越干”的恶性循环。",
                "缺油性干性肌肤的mm需注意，选择护肤品时不能单纯考虑补水，还要考虑补充油脂。建议使用含神经酰胺、透明脂酸等成分的高品质的外用保湿品，因为这两种成分不仅可减少表皮水分的蒸发，而且具有吸附水分的能力。"));
        waterOilModel3.waterOilResultModels.add(addWaterOilResultModel("干燥起皮", "缺油性干性肌肤的mm需注意，选择护肤品时不能单纯考虑补水，还要考虑补充油脂。" +
                "建议使用含神经酰胺、透明脂酸等成分的高品质的外用保湿品，因为这两种成分不仅可减少表皮水分的蒸发，而且具有吸附水分的能力。",
                "针对缺油缺水肤质，选择一款锁水保湿效果好的护肤品是非常重要的！由于皮肤本身缺少油脂，如果锁水功能做不好，" +
                        "给肌肤“喝”再多的水也是白费。针对干燥起皮问题，夏秋季选择保养护肤品时应当注意，尽量选择温和、低敏感的天然草本植物成分。"));
        WaterOilModel waterOilModel4 = addWaterOilModel("T", "水分缺失/缺油");
        waterOilModel4.waterOilResultModels.addAll(waterOilModel3.waterOilResultModels);
        waterOilModel4.waterOilResultModels.get(0).question = "皮肤屏障薄弱";
        WaterOilModel waterOilModel5 = addWaterOilModel("T", "水分正常/偏油");
        waterOilModel5.waterOilResultModels.add(addWaterOilResultModel("毛孔粗大", "皮肤出油异常，为了排出更多的油脂，毛孔会渐渐粗大、明显，肌肤渐渐粗糙。",
                "毛孔粗大补救方法：用温水洗脸，使用控油洗面奶后记得拍上爽肤水收敛毛孔和保湿面霜，另外还要定期做下保湿面膜。不吃油炸易上火垃圾食品，使用洗面奶或者一些含碱性的香皂。"));
        waterOilModel5.waterOilResultModels.add(addWaterOilResultModel("水油失衡", "肌肤具有自我调节水油平衡的能力。当肌肤调节能力失衡时，就会出现肌肤问题。皮肤偏油，即油脂分泌过多，皮肤往往缺水，即水油失衡。",
                "水油失衡，这时就需要补水、控油双管齐下才是真正理想的解决办法。偏油皮肤在选择控油产品时除了强调控油的效果外，还必须考虑产品本身的保湿、锁水效果。" +
                        "只有提供肌肤适度的不含油的滋润保湿，将肌肤调理到水油平衡的最佳状态，才能真正告别油光。"));
        waterOilModel5.waterOilResultModels.add(addWaterOilResultModel("T区有油光", "额头、鼻翼汗腺分布较多，容易出油，多是清洁不彻底和过度清洁造成的。",
                "T区平衡补水才是控油关键，控油并非将油脂全部去除，而应让正常肌肤的油脂和水分分泌处于一种平衡状态，洁面后要用使用具有平衡油脂分泌功能的爽肤水，为肌肤补水，让充足的水分滋润肌肤。"));
        waterOilModel5.waterOilResultModels.add(addWaterOilResultModel("肌肤出油严重", "肌肤出油严重是肌肤皮脂腺发达导致的。油脂有皮脂腺分泌，" +
                "顺着毛囊来到肌肤表面，形成一层保护膜。过多的油脂会粘附本应脱落的角质层，堵塞毛孔，引发粉刺和痤疮。",
                "肌肤出油严重要遵循水抑油的原理，要调理水油平衡，单纯控油还不够，补水锁水也很重要。理想的肌肤水油比例为4:1，如果水分不足，就算把毛孔中的油脂全清理掉，" +
                        "肌肤也会重新分泌大量油分来弥补。建议使用高渗透清爽质地的补水产品，肌肤的水分充足了，“产油”的动力也就小了。"));
        WaterOilModel waterOilModel6 = addWaterOilModel("T", "水分缺失/油分正常");
        waterOilModel6.waterOilResultModels.add(addWaterOilResultModel("干燥起皮", "皮肤严重缺乏水分，会感觉皮肤发紧，个别部位出现干燥脱皮的症状。",
                "做好以下五步，就能预防干燥起皮的皮肤问题，第一步：皮肤清洁；第二部：选对合适保湿用品；第三部：防晒工作不能忽视；第四部：补充好肌肤水分；" +
                        "第五部：合理的饮食习惯。缺水偏干肤质，很容易干燥起皮，夏秋季选择保养护肤品时应当注意，尽量选择温和、低敏感的天然草本植物成分。"));
        waterOilModel6.waterOilResultModels.add(addWaterOilResultModel("有老化迹象", "皮肤水分含量是皮肤的一项重要健康指标，皮肤含水量的减少，会导致肌肤抗衰老能力下降。",
                "建议尽早使用葡萄柚，木瓜果提取，辅酶Q10，水解纤维蛋白等抗老化成分的护肤产品，延缓肌肤衰老。" +
                        "平时要养成良好的生活习惯，加强皮肤保健补水，使用适当的护肤品能增加皮肤所需要的营养成分，促进皮肤健美，延缓皮肤衰老。"));
        waterOilModel6.waterOilResultModels.add(addWaterOilResultModel("外油内干", "缺水性偏干肌肤内部水分与皮脂失去平衡，导致皮肤反馈性地刺激皮脂腺分泌增加，造成一种“外油内干”的局面。",
                "外油内干肌改善方式是清洁、调理、保湿三大保养环节结合起来。清洁——每天洗脸前先用热水蒸脸，让毛孔充分打开，然后将洗面奶在手上打出泡沫，在脸上画圈清洁，之后用温水洗净。调理——清洁之后，" +
                        "用化妆棉蘸取化妆水，以螺旋状擦拭肌肤，有助老废角质脱落。保湿——蘸取乳液，从肌肤最干的部位开始，同样以螺旋状搽乳液，起到再度清洁和补水保湿的作用。"));
        waterOilModel6.waterOilResultModels.add(addWaterOilResultModel("小斑点", "缺水偏干皮肤缺少抑制黑色素形成的物质，导致黑色素在皮肤上大量聚集，最终形成小斑点。",
                "皮肤有小斑点，护肤品建议使用氢醌，壬二酸，曲酸等抑制黑色素形成的成分，减少黑色素生成。平时要做好防晒工作，对于电脑一族的人，要注意电脑辐射的侵害。"));
        WaterOilModel waterOilModel7 = addWaterOilModel("T", "水分缺失/偏油");
        waterOilModel7.waterOilResultModels.addAll(waterOilModel6.waterOilResultModels);
        WaterOilModel waterOilModel8 = addWaterOilModel("手", "水分正常/油分正常");
        waterOilModel8.waterOilResultModels.addAll(waterOilModel1.waterOilResultModels);
        WaterOilModel waterOilModel9 = addWaterOilModel("手", "十分水润/油分正常");
        waterOilModel9.waterOilResultModels.addAll(waterOilModel2.waterOilResultModels);
        WaterOilModel waterOilModel10 = addWaterOilModel("手", "水分正常/缺油");
        waterOilModel10.waterOilResultModels.addAll(waterOilModel3.waterOilResultModels);
        waterOilModel10.waterOilResultModels.get(3).detail = "皮肤严重缺乏水分，会感觉皮肤发紧，个别部位出现干燥脱皮的症状。年龄增长、气候变化、" +
                "睡眠不足、过度疲劳、洗澡水过热、洗涤用品碱性强等都是导致皮肤干燥脱皮的重要原因。";
        waterOilModel10.waterOilResultModels.get(3).solute = "针对缺油缺水肤质，选择一款锁水保湿效果好的护肤品是非常重要的！由于皮肤本身缺少油脂，如果锁水功能做不好，" +
                "给肌肤“喝”再多的水也是白费。针对干燥起皮问题，夏秋季选择保养护肤品时应当注意，尽量选择温和、低敏感的天然草本植物成分。";
        WaterOilModel waterOilModel11 = addWaterOilModel("手", "水分缺失/缺油");
        waterOilModel11.waterOilResultModels.addAll(waterOilModel10.waterOilResultModels);
        WaterOilModel waterOilModel12 = addWaterOilModel("手", "水分正常/偏油");
        waterOilModel12.waterOilResultModels.add(addWaterOilResultModel("毛孔堵塞", "皮肤出油异常，为了排出更多的油脂，毛孔会渐渐粗大、明显，肌肤渐渐粗糙。",
                "毛孔堵塞要加强日常皮肤组织的清洁和保养，多吃水果，不吃油炸易上火垃圾食品，使用洗面奶或者一些含碱性的香皂。毛孔堵塞推荐使用含啤酒花、红景天、黄芩的控油收敛产品。"));
        waterOilModel12.waterOilResultModels.add(addWaterOilResultModel("毛孔粗大", "皮肤出油异常，为了排出更多的油脂，毛孔会渐渐粗大、明显，肌肤渐渐粗糙。",
                "毛孔粗大补救方法：用温水洗脸，使用控油洗面奶后记得拍上爽肤水收敛毛孔和保湿面霜，另外还要定期做下保湿面膜。不吃油炸易上火垃圾食品，使用洗面奶或者一些含碱性的香皂。"));
        waterOilModel12.waterOilResultModels.add(addWaterOilResultModel("水油失衡", "肌肤具有自我调节水油平衡的能力。当肌肤调节能力失衡时，就会出现肌肤问题。皮肤偏油，即油脂分泌过多，皮肤往往缺水，即水油失衡。",
                "水油失衡，这时就需要补水、控油双管齐下才是真正理想的解决办法。偏油皮肤在选择控油产品时除了强调控油的效果外，" +
                        "还必须考虑产品本身的保湿、锁水效果。只有提供肌肤适度的不含油的滋润保湿，将肌肤调理到水油平衡的最佳状态，才能真正告别油光。"));
        waterOilModel12.waterOilResultModels.add(addWaterOilResultModel("肌肤出油严重", "肌肤出油严重是肌肤皮脂腺发达导致的。油脂有皮脂腺分泌，" +
                "顺着毛囊来到肌肤表面，形成一层保护膜。过多的油脂会粘附本应脱落的角质层，堵塞毛孔，引发粉刺和痤疮。",
                "肌肤出油严重要遵循水抑油的原理，要调理水油平衡，单纯控油还不够，补水锁水也很重要。理想的肌肤水油比例为4:1，如果水分不足，就算把毛孔中的油脂全清理掉，" +
                        "肌肤也会重新分泌大量油分来弥补。建议使用高渗透清爽质地的补水产品，肌肤的水分充足了，“产油”的动力也就小了。"));
        WaterOilModel waterOilModel13 = addWaterOilModel("手", "水分缺失/油分正常");
        waterOilModel13.waterOilResultModels.addAll(waterOilModel6.waterOilResultModels);
        WaterOilModel waterOilModel14 = addWaterOilModel("手", "水分缺失/偏油");
        waterOilModel14.waterOilResultModels.addAll(waterOilModel7.waterOilResultModels);
        WaterOilModel waterOilModel15 = addWaterOilModel("眼", "水分正常/油分正常");
        waterOilModel15.waterOilResultModels.addAll(waterOilModel1.waterOilResultModels);
        WaterOilModel waterOilModel16 = addWaterOilModel("眼", "十分水润/油分正常");
        waterOilModel16.waterOilResultModels.addAll(waterOilModel2.waterOilResultModels);
        WaterOilModel waterOilModel17 = addWaterOilModel("眼", "水分正常/缺油");
        waterOilModel17.waterOilResultModels.add(addWaterOilResultModel("肌肤屏障薄弱", "肌肤屏障薄弱是因为肌肤油脂分泌量不足，肌肤最外层是天然保护膜不够厚实，无法抵挡外界刺激物的伤害。",
                "肌肤屏障薄弱需要提高肌肤免疫力。建议使用含有角鲨烯、神经酰胺、荷荷芭酯等油脂成分的护肤品。作息规律、饮食合理。还有不能乱用含香料色素、矿物油及动物成分化妆品。"));
        waterOilModel17.waterOilResultModels.add(addWaterOilResultModel("红血丝", "皮肤缺水又缺油，最外层角质薄弱，皮肤薄而敏感，更容易受到外界刺激。",
                "红血丝的皮肤一般都是比较敏感的，所以不能用一些带有激素的产品。比如美白方面的产品激素比较重，脸上有红血丝避免使用。建议为肌肤选择含有角鲨烯、神经酰胺、荷荷芭酯等油脂成分的护肤品，加固肌肤屏障。"));
        waterOilModel17.waterOilResultModels.add(addWaterOilResultModel("缺水缺油", "缺油性偏干性肌肤的皮脂腺先天不足，不能分泌足够肌肤所需的油脂，" +
                "肌肤没有锁水能力，如果只靠单纯补水，容易造成“越补越干”的恶性循环。",
                "缺油性干性肌肤的mm需注意，选择护肤品时不能单纯考虑补水，还要考虑补充油脂。建议使用含神经酰胺、" +
                        "透明脂酸等成分的高品质的外用保湿品，因为这两种成分不仅可减少表皮水分的蒸发，而且具有吸附水分的能力。"));
        waterOilModel17.waterOilResultModels.add(addWaterOilResultModel("黑眼圈", "眼部肌肤较脆弱幼嫩，眼部肌肤缺水，再加上眼部血液循环不畅，就容易导致色素沉着，以致产生黑眼圈。",
                "黑眼圈成因多由于代谢不畅所致，所以促进眼周血液循环是王道，在使用眼周保养品的同时，必要的按摩可不能少。含有甜杏仁，水果及山茶叶精华的眼周保养品对于去除黑眼圈效果不错。"));
        WaterOilModel waterOilModel18 = addWaterOilModel("眼", "水分缺失/缺油");
        waterOilModel18.waterOilResultModels.addAll(waterOilModel17.waterOilResultModels);
        WaterOilModel waterOilModel19 = addWaterOilModel("眼", "水分正常/偏油");
        waterOilModel19.waterOilResultModels.add(addWaterOilResultModel("毛孔堵塞", "皮肤出油异常，为了排出更多的油脂，毛孔会渐渐粗大、明显，肌肤渐渐粗糙。",
                "毛孔堵塞要加强日常皮肤组织的清洁和保养，多吃水果，不吃油炸易上火垃圾食品，使用洗面奶或者一些含碱性的香皂。毛孔堵塞推荐使用含啤酒花、红景天、黄芩的控油收敛产品。"));
        waterOilModel19.waterOilResultModels.add(addWaterOilResultModel("毛孔粗大", "皮肤出油异常，为了排出更多的油脂，毛孔会渐渐粗大、明显，肌肤渐渐粗糙。",
                "毛孔粗大补救方法：用温水洗脸，使用控油洗面奶后记得拍上爽肤水收敛毛孔和保湿面霜，另外还要定期做下保湿面膜。不吃油炸易上火垃圾食品，使用洗面奶或者一些含碱性的香皂。"));
        waterOilModel19.waterOilResultModels.add(addWaterOilResultModel("水油失衡", "肌肤具有自我调节水油平衡的能力。当肌肤调节能力失衡时，就会出现肌肤问题。皮肤偏油，即油脂分泌过多，皮肤往往缺水，即水油失衡。",
                "水油失衡，这时就需要补水、控油双管齐下才是真正理想的解决办法。偏油皮肤在选择控油产品时除了强调控油的效果外，" +
                        "还必须考虑产品本身的保湿、锁水效果。只有提供肌肤适度的不含油的滋润保湿，将肌肤调理到水油平衡的最佳状态，才能真正告别油光。"));
        waterOilModel19.waterOilResultModels.add(addWaterOilResultModel("油脂分泌旺盛", "油脂分泌旺盛一般与遗传，内分泌，外部环境有关，常易吸附灰尘，堵塞毛孔，易长痤疮，易患脂溢性皮炎，油腻让人感到不舒服。",
                "一周要做一次去除角质疏通毛孔，做好保湿抑制油脂，选择具有控油、深层清洁毛孔功效的护肤品，不仅要能吸走肌肤表层油脂及毛孔内的脏物，还要有平衡皮肤的酸碱度，从而起到收敛毛孔的效果。"));

        WaterOilModel waterOilModel20 = addWaterOilModel("眼", "水分缺失/油分正常");
        waterOilModel20.waterOilResultModels.add(addWaterOilResultModel("干燥起皮", "皮肤严重缺乏水分，会感觉皮肤发紧，个别部位出现干燥脱皮的症状。",
                "做好以下五步，就能预防干燥起皮的皮肤问题，第一步：皮肤清洁；第二部：选对合适保湿用品；第三部：防晒工作不能忽视；第四部：补充好肌肤水分；" +
                        "第五部：合理的饮食习惯。缺水偏干肤质，很容易干燥起皮，夏秋季选择保养护肤品时应当注意，尽量选择温和、低敏感的天然草本植物成分。"));
        waterOilModel20.waterOilResultModels.add(addWaterOilResultModel("有老化迹象", "皮肤水分含量是皮肤的一项重要健康指标，皮肤含水量的减少，会导致肌肤抗衰老能力下降。",
                "建议尽早使用葡萄柚，木瓜果提取，辅酶Q10，水解纤维蛋白等抗老化成分的护肤产品，延缓肌肤衰老。" +
                        "平时要养成良好的生活习惯，加强皮肤保健补水，使用适当的护肤品能增加皮肤所需要的营养成分，促进皮肤健美，延缓皮肤衰老。"));
        waterOilModel20.waterOilResultModels.add(addWaterOilResultModel("外油内干", "缺水性偏干肌肤内部水分与皮脂失去平衡，导致皮肤反馈性地刺激皮脂腺分泌增加，造成一种“外油内干”的局面。",
                "外油内干肌改善方式是清洁、调理、保湿三大保养环节结合起来。清洁——每天洗脸前先用热水蒸脸，让毛孔充分打开，然后将洗面奶在手上打出泡沫，在脸上画圈清洁，之后用温水洗净。调理——清洁之后，" +
                        "用化妆棉蘸取化妆水，以螺旋状擦拭肌肤，有助老废角质脱落。保湿——蘸取乳液，从肌肤最干的部位开始，同样以螺旋状搽乳液，起到再度清洁和补水保湿的作用。"));
        waterOilModel20.waterOilResultModels.add(addWaterOilResultModel("黑眼圈", "眼部肌肤较脆弱幼嫩，眼部肌肤缺水，再加上眼部血液循环不畅，就容易导致色素沉着，以致产生黑眼圈。",
                "黑眼圈成因多由于代谢不畅所致，所以促进眼周血液循环是王道，在使用眼周保养品的同时，必要的按摩可不能少。含有甜杏仁，水果及山茶叶精华的眼周保养品对于去除黑眼圈效果不错。"));

        WaterOilModel waterOilModel21 = addWaterOilModel("眼", "水分缺失/偏油");
        waterOilModel21.waterOilResultModels.addAll(waterOilModel20.waterOilResultModels);

        waterOilModels.add(waterOilModel1);
        waterOilModels.add(waterOilModel2);
        waterOilModels.add(waterOilModel3);
        waterOilModels.add(waterOilModel4);
        waterOilModels.add(waterOilModel5);
        waterOilModels.add(waterOilModel6);
        waterOilModels.add(waterOilModel7);
        waterOilModels.add(waterOilModel8);
        waterOilModels.add(waterOilModel9);
        waterOilModels.add(waterOilModel10);
        waterOilModels.add(waterOilModel11);
        waterOilModels.add(waterOilModel12);
        waterOilModels.add(waterOilModel13);
        waterOilModels.add(waterOilModel14);
        waterOilModels.add(waterOilModel15);
        waterOilModels.add(waterOilModel16);
        waterOilModels.add(waterOilModel17);
        waterOilModels.add(waterOilModel18);
        waterOilModels.add(waterOilModel19);
        waterOilModels.add(waterOilModel20);
        waterOilModels.add(waterOilModel21);
    }

    public static WaterOilModel addWaterOilModel(String order, String type){
        WaterOilModel waterOilModel = new WaterOilModel();
        waterOilModel.order = order;
        waterOilModel.type = type;
        return waterOilModel;
    }

    public static WaterOilResultModel addWaterOilResultModel(String question, String detail, String solute){
        WaterOilResultModel waterOilResultModel = new WaterOilResultModel();
        waterOilResultModel.question = question;
        waterOilResultModel.detail = detail;
        waterOilResultModel.solute = solute;
        return waterOilResultModel;
    }

    public static WaterOilModel getWaterOilModel(String order, String type){
        for(WaterOilModel waterOil : waterOilModels){
            if(order.equals(waterOil.order) && type.equals(waterOil.type)){
                return waterOil;
            }
        }
        return null;
    }

}
