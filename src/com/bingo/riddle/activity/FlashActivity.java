package com.bingo.riddle.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.bingo.lantern.R;
import com.bingo.riddle.db.DBRiddleManager;
import com.bingo.riddle.model.Riddle;

public class FlashActivity extends Activity {
	private Timer timer;

	// private ViewGroup container;

	// 关于数据操作
	private DBRiddleManager dbRManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				initView();
			}
		};
		timer.schedule(task, 1000 * 5);

		// 数据库操作
		/* 开启一个新线程，在新线程里执行耗时的方法 */
		new Thread(new Runnable() {
			public void run() {
				// if(AppInstalledUtil.isAppInstalled(getApplicationContext(),
				// "com.bingo.lattern"))
				// {
				// System.out.println("========已安装！==");
				// }
				// else {
				// initData();
				// }

				SharedPreferences sharedPreferences = getSharedPreferences(
						"share", MODE_PRIVATE);

				boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun",
						true);
				Editor editor = sharedPreferences.edit();

				if (isFirstRun) {
					Log.e("debug", "第一次运行");
					editor.putBoolean("isFirstRun", false);
					editor.commit();
					initData();
				} else {
					Log.e("debug", "不是第一次运行");
				}
				// 耗时的方法
			}
		}).start();

	}

	private void initView() {
		Intent intent = new Intent(FlashActivity.this,
				MainFragmentActivity.class);
		startActivity(intent);

		this.finish();
	}

	private void initData() {
		dbRManager = new DBRiddleManager(this);

		Riddle riddle1 = new Riddle("	烈士美名传千古(打一诗句) 	", "	 留取丹心照汗青	", "经典", "无");
		Riddle riddle2 = new Riddle("	孝心感动天(打一地名) 	", "	 孝感	", "经典", "无");
		Riddle riddle3 = new Riddle("	臭豆腐(打一个明星名字) 	", "	 莫文蔚(莫闻味)	", "经典", "无");
		Riddle riddle4 = new Riddle("	小姑娘,猜一个字 	", "	 妙	", "经典", "无");
		Riddle riddle5 = new Riddle("	冷冻室(打一礼貌用语) 	", "	 寒舍	", "经典", "无");
		Riddle riddle6 = new Riddle("	什么书你最让人嫉妒？(猜一猜) 	", "	 秘书	", "经典", "无");
		Riddle riddle7 = new Riddle("	一个老头不睡觉，从不说话把头摇(打一物) 	", "	 不倒翁	", "经典",
				"无");
		Riddle riddle8 = new Riddle("	哪里的妞最多 (猜一猜)	", "	 奶牛(妞)场	", "经典", "无");
		Riddle riddle9 = new Riddle("	望儿山上众人行，慈母馆内缅恩情(打一节日) 	", "	 母亲节	", "经典",
				"无");
		Riddle riddle10 = new Riddle("	最新什么路不能走 (猜一猜) 	", "	 寻常路(不走寻常路)	",
				"经典", "无");
		Riddle riddle11 = new Riddle("	男人之所(打一国外城市) 	", "	 汉城	", "经典", "无");
		Riddle riddle12 = new Riddle("	妇女们在不知不觉中丢失掉的东西是什么？(猜一猜) 	", "	 美貌	",
				"经典", "无");
		Riddle riddle13 = new Riddle("	航空公司开张(猜一成语) 	", "	 有机可乘	", "经典", "无");
		Riddle riddle14 = new Riddle("	隔河相望，终到相聚时(打一七夕景观) 	", "	 牵牛星、织女星相会	",
				"经典", "无");
		Riddle riddle15 = new Riddle("	大伟在电影最精彩的时候却上厕所，为什么？(猜一猜) 	",
				"	 因为大伟没有看电影	", "经典", "无");
		Riddle riddle16 = new Riddle(
				"	凸眼睛，阔嘴巴，尾巴要比身体大，碧绿水草衬着它，好像一朵大红花。(打一动物)	", "	 金鱼	", "经典", "无");
		Riddle riddle17 = new Riddle("	虚心向人请教(打一学科) 	", "	 化学	", "经典", "无");
		Riddle riddle18 = new Riddle("	风平浪静(打一城市名) 	", "	 宁波	", "经典", "无");
		Riddle riddle19 = new Riddle("	相反世界(打一物品) 	", "	 镜子	", "经典", "无");
		Riddle riddle20 = new Riddle("	捂(打一电影名) 	", "	 我的左手	", "经典", "无");
		Riddle riddle21 = new Riddle("	红红如玛瑙，营养又美味(打一水果) 	", "	 樱桃	", "经典", "无");
		Riddle riddle22 = new Riddle("	别听鬼话(打一离合字) 	", "	 信人言	", "经典", "无");
		Riddle riddle23 = new Riddle("	徒留昔日的帝王黯然神伤(打一明星) 	", "	 刘惜君	", "经典",
				"无");
		Riddle riddle24 = new Riddle("	新媳妇下花轿(打一成语) 	", "	 任人摆布	", "经典", "无");
		Riddle riddle25 = new Riddle("	自小在一起，目前少联系(猜一字) 	", "	 省	", "经典", "无");
		Riddle riddle26 = new Riddle("	曹植因何七步诗 (三字常言一) 	", "	 老大难	", "经典", "无");
		Riddle riddle27 = new Riddle("	貂蝉莫垂涕(打一热门电视剧) 	", "	 《美人无泪》	", "经典",
				"无");
		Riddle riddle28 = new Riddle("	天下第一 (打香烟商标一) 	", "	 大前门	", "经典", "无");
		Riddle riddle29 = new Riddle("	初始的日子(打一节日) 	", "	 元旦	", "经典", "无");
		Riddle riddle30 = new Riddle("	拱手让人 (猜一字) 	", "	 供	", "经典", "无");
		Riddle riddle31 = new Riddle("	来了施拳脚(打一湖南地名) 	", "	 临武	", "经典", "无");
		Riddle riddle32 = new Riddle("	没有住人的城市(打一歌曲名) 	", "	 《空城》	", "经典", "无");
		Riddle riddle33 = new Riddle("	自杀之都(打一外国城市) 	", "	 拉斯维加斯	", "经典", "无");
		Riddle riddle34 = new Riddle("	两地相思 (猜一字) 	", "	 桂	", "经典", "无");
		Riddle riddle35 = new Riddle("	太平间的解剖医生(打一离合字) 	", "	 屠尸者	", "经典", "无");
		Riddle riddle36 = new Riddle("	四处游荡走不掉，人人需要它围绕(打一自然物) 	", "	 空气	",
				"经典", "无");
		Riddle riddle37 = new Riddle("	冰到底是什么东西？ (猜一猜)	", "	 一块一块的水	", "经典",
				"无");
		Riddle riddle38 = new Riddle("	水陆各半(打一拉丁美洲国家名) 	", "	 海地	", "经典", "无");
		Riddle riddle39 = new Riddle("	古老乐器民族舞，苗族文化入人心(打一乐器) 	", "	 芦笙	", "经典",
				"无");
		Riddle riddle40 = new Riddle("	轻柔丝滑如蝉翼，苏杭一带最有名(打一物品) 	", "	 丝绸	", "经典",
				"无");
		Riddle riddle41 = new Riddle("	美美晚餐好心情，熊熊火炉暖人心(打一国外节日) 	", "	 平安夜	",
				"经典", "无");
		Riddle riddle42 = new Riddle("	春暖花开来踏青，寒食上墓忆前人(打一节日) 	", "	 清明节	",
				"经典", "无");
		Riddle riddle43 = new Riddle("	古人对月亮的崇拜(打一节日) 	", "	 中秋节	", "经典", "无");
		Riddle riddle44 = new Riddle("	落叶乔木，见血封喉(打一植物) 	", "	 毒箭树	", "经典", "无");
		Riddle riddle45 = new Riddle("	绿豆自杀，从六楼跳了下来,流了很多血,怎么样了？ (猜一猜)	",
				"	 变成了红豆	", "经典", "无");
		Riddle riddle46 = new Riddle("	张着小雨伞,常站大树下，干湿都能吃，味美营养大(打一菌类) 	",
				"	 蘑菇	", "经典", "无");
		Riddle riddle47 = new Riddle("	小小绿叶水里生，水珠爱在上面玩(打一植物) 	", "	 荷叶	", "经典",
				"无");
		Riddle riddle48 = new Riddle("	一件东西大无边，能装三百多个天，还装月亮十二个，它换衣服过新年。(打一物)	",
				"	 日历	", "经典", "无");
		Riddle riddle49 = new Riddle("	时而成烟，时而成线，遇见了海，还能变盐(打一自然物体) 	", "	 水	",
				"经典", "无");
		Riddle riddle50 = new Riddle(
				"	此物老家在非洲，力大气壮赛过牛，血盆大口吼一声，吓得百兽都发抖。(打一动物)	", "	 狮子	", "经典", "无");
		Riddle riddle51 = new Riddle("	心惊胆战(打一离合字) 	", "	 心具惧	", "经典", "无");
		Riddle riddle52 = new Riddle("	整天都是太阳(打一城市) 	", "	 拉萨(拉萨称为“日光城”)	",
				"经典", "无");
		Riddle riddle53 = new Riddle("	十个哥哥 ,猜一个字 	", "	 克	", "经典", "无");
		Riddle riddle54 = new Riddle("	沙丘之上，烽火不停(打一诗句) 	", "	 《使至塞上》	", "经典",
				"无");
		Riddle riddle55 = new Riddle("	反胃(打三字常用语) 	", "	 吃不消	", "经典", "无");
		Riddle riddle56 = new Riddle("	白胖娃娃，又圆又滑，东蹦西跳，总是挨打 	", "	 乒乓球	", "经典",
				"无");
		Riddle riddle57 = new Riddle("	掘墓鞭尸报父仇，门客挖眼挂东门(打一历史人物) 	", "	 伍子胥	",
				"经典", "无");
		Riddle riddle58 = new Riddle("	输氧(打一离合字) 	", "	 乞一气	", "经典", "无");
		Riddle riddle59 = new Riddle(
				"	饭店里有三个女人在吃香肠：一个是咬着吃的，一个是舔着吃的，一个是裹着吃的，请问这三个女人哪个结过婚？(猜一猜) 	",
				"	 戴戒指的那个女人结过婚	", "经典", "无");
		Riddle riddle60 = new Riddle("	小明和朋友去海边玩，突然海浪把小明卷走，请问小明的朋友叫什么？ (猜一猜)	",
				"	 叫救命	", "经典", "无");
		Riddle riddle61 = new Riddle("	晚唐长安诞才女，未入正史传千古(打一女诗人) 	", "	 鱼玄机	",
				"经典", "无");
		Riddle riddle62 = new Riddle("	海市蜃楼(打一李白诗句) 	", "	 眼前有景道不得	", "经典", "无");
		Riddle riddle63 = new Riddle("	既能投笔又能从戎 (猜一猜)	", "	 刘	", "经典", "无");
		Riddle riddle64 = new Riddle("	翻跟斗最在行，长有六张脸，还有二十一只眼(打一物) 	", "	 骰子	",
				"经典", "无");
		Riddle riddle65 = new Riddle("	身子黑不溜秋，喜往泥里嬉游，常爱口吐气泡，能够观察气候。(打一动物)	",
				"	 泥鳅	", "经典", "无");
		Riddle riddle66 = new Riddle("	脱去黄金袍，露出白玉体，身子比豆小，名字有三尺(猜一猜) 	",
				"	 大米	", "经典", "无");
		Riddle riddle67 = new Riddle("	女性的内裤(打一食品) 	", "	 果冻(食品)	", "经典", "无");
		Riddle riddle68 = new Riddle("	撼山易,撼岳家军难!(打一清代文人) 	", "	 金圣叹	", "经典",
				"无");
		Riddle riddle69 = new Riddle("	是自己的，却是别人用的最多(打一事物) 	", "	 名字	", "经典",
				"无");
		Riddle riddle70 = new Riddle("	静姝一枚，小家碧玉(打一奥运明星) 	", "	 吴静钰	", "经典",
				"无");
		Riddle riddle71 = new Riddle("	三月节里何所期，真心日月故人离(打一节日) 	", "	 清明节	",
				"经典", "无");
		Riddle riddle72 = new Riddle("	春耕春种好时节，墓祭之礼把情谢(打一节日) 	", "	 清明节	",
				"经典", "无");
		Riddle riddle73 = new Riddle("	一孔之见(打一物品) 	", "	 显微镜	", "经典", "无");
		Riddle riddle74 = new Riddle("	作家报警(打一离合字) 	", "	 文求救	", "经典", "无");
		Riddle riddle75 = new Riddle("	草原上的女王城(打一外国城市) 	", "	 丹佛	", "经典", "无");
		Riddle riddle76 = new Riddle("	在门口流了一夜的鼻涕(打一成语) 	", "	 一夜风流	", "经典",
				"无");
		Riddle riddle77 = new Riddle("	坏人聚居，无人举报。(打一地址) 	", "	 监狱	", "经典", "无");
		Riddle riddle78 = new Riddle("	玩Blog的高手(打一火热电视剧) 	", "	 《微博达人》	", "经典",
				"无");
		Riddle riddle79 = new Riddle("	梦回归到烟雨之中(打一地区) 	", "	 江南	", "经典", "无");
		Riddle riddle80 = new Riddle("	梅花满天飞，芳香扑鼻来(打一诗句) 	", "	 遥知不是雪，为有暗香来	",
				"经典", "无");

		Riddle riddle81 = new Riddle("	青蛙为什么能比树跳得高？(猜一猜) 	", "	 因为树不会跳	", "动物",
				"无");
		Riddle riddle82 = new Riddle("	小小白姑娘,住在硬壳乡,做成盘中餐,味美扑鼻香(打一动物) 	",
				"	 田螺	", "动物", "无");
		Riddle riddle83 = new Riddle("	穿着盔甲爱钻洞，全身是宝大家爱(打一动物) 	", "	 穿山甲	",
				"动物", "无");
		Riddle riddle84 = new Riddle("	美丽无比似女神，求爱时来把屏开(打一动物) 	", "	 孔雀	", "动物",
				"无");
		Riddle riddle85 = new Riddle("	朝为越溪女，暮作吴宫妃探骊格 (猜一猜) 	", "	 泊人·时迁	",
				"动物", "无");
		Riddle riddle86 = new Riddle("	贼头贼脑爱偷油,花猫一叫赶紧溜(打一动物) 	", "	 老鼠	", "动物",
				"无");
		Riddle riddle87 = new Riddle("	五毒之首千足虫，若咬一口鲜血涌(打一动物) 	", "	 蜈蚣	", "动物",
				"无");
		Riddle riddle88 = new Riddle("	远看像只猫，捕猎身手好。游泳又爬树，不吃青青草(打一动物) 	",
				"	 豹	", "动物", "无");
		Riddle riddle89 = new Riddle("	生肖中，最骇人的是哪3个？(猜一猜) 	", "	 猪、龙、鸡(朱熔基)	",
				"动物", "无");
		Riddle riddle90 = new Riddle("	有头没有颈，身上冷冰冰，有翅不能飞，无脚也能行(打一动物)。 	",
				"	 鱼	", "动物", "无");
		Riddle riddle91 = new Riddle("	懂得 (打一动物) 	", "	 知了(蝉)	", "动物", "无");
		Riddle riddle92 = new Riddle("	小英雄，爱捉虫，冬天一来躲进洞(打一动物) 	", "	 青蛙	", "动物",
				"无");
		Riddle riddle93 = new Riddle(
				"	身穿白袍戴红帽，脖长走路摇啊摇。扁嘴有翅水陆行，红掌爱把清波扰。(打一动物)	", "	 鹅	", "动物", "无");
		Riddle riddle94 = new Riddle("	左右不分，正反不知(打一动物) 	", "	 鱼	", "动物", "无");
		Riddle riddle95 = new Riddle("	羽毛鲜艳呈七色，却爱花粉小八哥(打一动物) 	", "	 虹彩吸蜜鹦鹉	",
				"动物", "无");
		Riddle riddle96 = new Riddle("	黑黑眼圈大胖子，最爱林中把竹吃(打一动物) 	", "	 熊猫	", "动物",
				"无");
		Riddle riddle97 = new Riddle("	徐妃用好酒(打一动物) 	", "	 蛐蟮	", "动物", "无");
		Riddle riddle98 = new Riddle("	有脚不会走路(打一动物) 	", "	 蜻蜓	", "动物", "无");
		Riddle riddle99 = new Riddle("	卸甲方知年事高(打一动物) 	", "	 牛	", "动物", "无");
		Riddle riddle100 = new Riddle("	敏锐眼睛弯弯嘴，用爪捕食它最会(打一动物) 	", "	 老鹰	",
				"动物", "无");
		Riddle riddle101 = new Riddle("	海中狼(打一动物) 	", "	 鲨鱼	", "动物", "无");
		Riddle riddle102 = new Riddle("	一位小姑娘,身穿花衣裳，百花是朋友，春天聚会忙(打一昆虫) 	",
				"	 蝴蝶	", "动物", "无");
		Riddle riddle103 = new Riddle("	身边嗅一嗅,专把毒品搜,警察夸奖它,是个好帮手(打一动物) 	",
				"	 缉毒犬	", "动物", "无");
		Riddle riddle104 = new Riddle("	左边有一只公羊右边有一只母羊中间有一只小羊为什么狼吃了小羊	",
				"	 公羊和母羊都是雕塑	", "动物", "无");
		Riddle riddle105 = new Riddle("	一个长鼻子，长长猴尾巴，变色龙眼睛，夜夜水中游(打一动物) 	",
				"	 海马	", "动物", "无");
		Riddle riddle106 = new Riddle("	春天睡觉，夏天出来，趴在树上，整天吵闹(打一昆虫动物) 	",
				"	 蝉(知了)	", "动物", "无");
		Riddle riddle107 = new Riddle("	小小鱼儿五个角(打一动物) 	", "	 海星	", "动物", "无");
		Riddle riddle108 = new Riddle("	身笨力气大，干活常带枷，春耕和秋种，不能缺少它。(打一动物) 	",
				"	 牛	", "动物", "无");
		Riddle riddle109 = new Riddle("	迎风飞千里,能把信息送,城市和门牌,它都记得清(打一动物) 	",
				"	 信鸽	", "动物", "无");
		Riddle riddle110 = new Riddle("	爱游泳，季节变，皮毛跟着变(打一动物) 	", "	 水獭	", "动物",
				"无");
		Riddle riddle111 = new Riddle(
				"	一位姑娘真漂亮,橘红衣裳披上身.嵌上七颗黑星星，蚜虫见了无影踪。(打一动物)	", "	 瓢虫	", "动物", "无");
		Riddle riddle112 = new Riddle(
				"	鹿的角，牛的蹄，天生喜水又喜泥。骆驼脖子驴的尾，回到故乡来定居。( 打一动物)	", "	 麋鹿	", "动物", "无");
		Riddle riddle113 = new Riddle("	黑身红嘴卷羽毛，长脖像2很高贵(打一鸟类) 	", "	 黑天鹅	",
				"动物", "无");
		Riddle riddle114 = new Riddle("	关公的命很不好，这是为什么啊？ (猜一猜)	",
				"	 红颜薄命(红脸关公)	", "动物", "无");
		Riddle riddle115 = new Riddle("	引力检测(打一动物) 	", "	 考拉	", "动物", "无");
		Riddle riddle116 = new Riddle("	一生都在忙,飞在百花乡,围着花儿转，花汁变蜜糖(打一昆虫) 	",
				"	 蜜蜂	", "动物", "无");
		Riddle riddle117 = new Riddle("	长着尖尖牙，森林称霸王(打一动物) 	", "	 老虎	", "动物",
				"无");
		Riddle riddle118 = new Riddle("	什么东西装玻璃，爱把鼻子当马骑？ (猜一猜)	", "	 眼镜	",
				"动物", "无");
		Riddle riddle119 = new Riddle("	长长脖子同树高，身披白纹褐斑点，又长又细四条腿(打一动物) 	",
				"	 长颈鹿	", "动物", "无");
		Riddle riddle120 = new Riddle("	小小飞蛾好夜游，闪闪屁股诱异性(打一动物) 	", "	 萤火虫	",
				"动物", "无");
		Riddle riddle121 = new Riddle("	绿色刀郎三角头，大眼明亮雌性强(打一动物) 	", "	 螳螂	",
				"动物", "无");
		Riddle riddle122 = new Riddle("	养的最多的鸡是什么鸡 (猜一猜)	", "	 手机(手鸡 人手一部)	",
				"动物", "无");
		Riddle riddle123 = new Riddle("	大大红掌水中摆，长长脖子一排排(打一动物) 	", "	 鹅	", "动物",
				"无");
		Riddle riddle124 = new Riddle("	浑身雪翼腿儿黑，最喜成群上青天(打一动物) 	", "	 白鹭	",
				"动物", "无");
		Riddle riddle125 = new Riddle(
				"	题目：昨夜蟋蟀鸣不停，惊回梦里已三更。起来独自绕街行，悄悄窗外月胧明。(猜一猜)	", "	 老鼠	", "动物",
				"无");
		Riddle riddle126 = new Riddle("	头顶大黑帽，身着燕尾服。(打一动物) 	", "	 企鹅	", "动物",
				"无");
		Riddle riddle127 = new Riddle("	败得真窝囊(打一动物) 	", "	 北极熊	", "动物", "无");
		Riddle riddle128 = new Riddle("	下面出场的这位嘉宾可是我们中国男人的骄傲,是个歌星,你猜是谁 	",
				"	 古巨鸡	", "动物", "无");
		Riddle riddle129 = new Riddle("	谁最喜欢咬文嚼字？ (猜一猜)	", "	 蛀书虫	", "动物", "无");
		Riddle riddle130 = new Riddle("	什么东西越削越粗 (猜一猜)	", "	 井、土削的越多，洞就越大(粗)	",
				"动物", "无");
		Riddle riddle131 = new Riddle("	色彩鲜艳丛中飞，鳞片雨衣格外美(打一动物) 	", "	 蝴蝶	",
				"动物", "无");
		Riddle riddle132 = new Riddle("	全身雪白尾翼黑，红冠细腿白富美(打一动物) 	", "	 丹顶鹤	",
				"动物", "无");
		Riddle riddle133 = new Riddle("	妈妈长得怪，肚前挂口袋，蹦蹦跳跳真可爱(打一动物) 	", "	 袋鼠	",
				"动物", "无");
		Riddle riddle134 = new Riddle("	小小家伙眼睛亮，花中采宝回家酿(打一动物) 	", "	 蜜蜂	",
				"动物", "无");
		Riddle riddle135 = new Riddle("	珍贵的动物打一生肖 (猜一猜)	", "	 龙	", "动物", "无");
		Riddle riddle136 = new Riddle(
				"	弯弯嘴儿亮眼睛，捕捉野兔它最行；千里眼儿看得清，一口吞下便清零(打一动物)	", "	 鹰	", "动物", "无");
		Riddle riddle137 = new Riddle("	驼背老公公，胡子毛烘烘，热火锅里去洗澡，青袍换成大给红袍。 (猜一猜)	",
				"	 虾	", "动物", "无");
		Riddle riddle138 = new Riddle("	古时明月虫影飞，世代草木永相随(打一动物)	",
				"	 蝴蝶(虫+古+月=蝴，虫+世+木=蝶)	", "动物", "无");
		Riddle riddle139 = new Riddle("	脖子长，爱吃鱼，天天吃鱼吐出来(打一动物) 	", "	 鸬鹚	",
				"动物", "无");
		Riddle riddle140 = new Riddle("	虽然不说话,本领呱呱叫,鼻子特别灵,破案功劳大(打一动物) 	",
				"	 警犬	", "动物", "无");
		Riddle riddle141 = new Riddle(
				"	软软绒毛白又白,两只耳朵竖起来。红红眼睛三瓣嘴，爱吃萝卜爱吃菜(打一动物)	", "	 小白兔	", "动物", "无");
		Riddle riddle142 = new Riddle("	前有毒夹，后有尾巴， 全身二十一节，中药铺要它。(打一动物) 	",
				"	 蜈蚣	", "动物", "无");
		Riddle riddle143 = new Riddle("	小小个头三尺高，头顶羽冠模样俏，爱把锦袍来炫耀(打一动物) 	",
				"	 孔雀	", "动物", "无");
		Riddle riddle144 = new Riddle("	小笋头上冒绿芽(打一动物) 	", "	 竹叶青	", "动物", "无");
		Riddle riddle145 = new Riddle("	神来帽脱下 ，革命力量大  (打两个字) 	", "	 米勒	", "动物",
				"无");
		Riddle riddle146 = new Riddle("	北岳恒山探骊格 (猜一猜)	", "	 名胜·丈人峰	", "动物", "无");
		Riddle riddle147 = new Riddle(
				"	一个白发老妈妈，走起路来四边爬，不用铁鎝不用锄，种下一片好芝麻(打一动物)	", "	 蚕蛾	", "动物", "无");
		Riddle riddle148 = new Riddle("	旭日阳刚成员主唱原创单曲(打一动物) 	", "	 麻雀	", "动物",
				"无");
		Riddle riddle149 = new Riddle("	小时候有尾巴，长大后尾巴不见了(打一动物) 	", "	 青蛙	",
				"动物", "无");
		Riddle riddle150 = new Riddle("	天上的眼睛掉进水里(打一动物) 	", "	 海星	", "动物", "无");
		Riddle riddle151 = new Riddle("	最接近人类的动物是什么？(猜一猜) 	", "	 虱子	", "动物",
				"无");
		Riddle riddle152 = new Riddle(
				"	过去拉犁干活快,如今栏里挤牛奶。过去拉粮几千斤,如今肉嫩餐桌摆(打一动物)	", "	 牛	", "动物", "无");
		Riddle riddle153 = new Riddle("	浑身肌肉最爱跳，胸前大兜装宝宝(打一动物) 	", "	 袋鼠	",
				"动物", "无");
		Riddle riddle154 = new Riddle("	小小蘑菇海里游，会发光来不怕黑(打一动物) 	", "	 水母	",
				"动物", "无");
		Riddle riddle155 = new Riddle("	羊，猪，熊，狗，猴哪一个动物用眉毛呼吸 	",
				"	 羊(扬(羊)眉吐气)	", "动物", "无");
		Riddle riddle156 = new Riddle("	双亲不同种，杂交不生育(打一动物) 	", "	 骡子	", "动物",
				"无");
		Riddle riddle157 = new Riddle("	刚出生的婴儿(打一动物) 	", "	 猫头鹰(毛头婴)	", "动物",
				"无");
		Riddle riddle158 = new Riddle("	小小一头牛,不拉犁和耧。力气虽不大，背着房子走(打一动物) 	",
				"	 蜗牛	", "动物", "无");
		Riddle riddle159 = new Riddle("	小小脑袋三根毛，五彩羽毛似扇子(打一动物) 	", "	 孔雀	",
				"动物", "无");
		Riddle riddle160 = new Riddle("	形似小飞机,飞东又飞西。夏天吃蚊虫，还能报天气。(打一动物) 	",
				"	 蜻蜓	", "动物", "无");

		Riddle riddle161 = new Riddle("	八姑娘(打一称谓) 	", "	 空中小姐	", "爱情", "无");
		Riddle riddle162 = new Riddle("	姑娘月下摆瓜果，祈求收获好姻缘。(打一节日) 	", "	 七夕节	",
				"爱情", "无");
		Riddle riddle163 = new Riddle("	有一个人到国外去，为什么他周围的都是中国人？(猜一猜) 	",
				"	 外国人到了中国	", "爱情", "无");
		Riddle riddle164 = new Riddle("	在厕所遇见朋友时，最好不要问哪一句话？(猜一猜) 	", "	 你吃了吗	",
				"爱情", "无");
		Riddle riddle165 = new Riddle("	人为什么要走去床上睡觉呢 (猜一猜)	", "	 因为床不会自己走过来	",
				"爱情", "无");
		Riddle riddle166 = new Riddle("	没人理 打一明星 (猜一猜) 	", "	 任贤齐	", "爱情", "无");
		Riddle riddle167 = new Riddle("	湖中把手分(打一国家) 	", "	 古巴	", "爱情", "无");
		Riddle riddle168 = new Riddle("	妻子认错(打一服装) 	", "	 太太服	", "爱情", "无");
		Riddle riddle169 = new Riddle("	纤夫的爱(打三字常用语) 	", "	 拉人情	", "爱情", "无");
		Riddle riddle170 = new Riddle("	妈妈最讨厌哪种鸭蛋？ (猜一猜)	", "	 考卷上的鸭蛋	", "爱情",
				"无");
		Riddle riddle171 = new Riddle("	七仙女爱上董永(打一诗句) 	", "	 只羡鸳鸯不羡仙	", "爱情",
				"无");
		Riddle riddle172 = new Riddle(
				"	寒山寺上一棵竹，不能做称有人用，此言非虚能兑现，只要有情雨下显，天鹅一出鸟不见。 (猜一猜)	", "	 等你说爱我	",
				"爱情", "无");
		Riddle riddle173 = new Riddle(
				"	日长夜短愁几许，高处无口几人来，一人游弋芳草地，十士脚长披蓑衣，天鹅展翅鸟已飞，白勺烹酒无意义，空余一钩三点雨! (猜一猜)	",
				"	 月亮代表我的心	", "爱情", "无");
		Riddle riddle174 = new Riddle(
				"	鸟飞鹅跳，月上中梢，目上朱砂，已异非巳，勺旁傍白，万事开头，工戈不全，雨下挚友，称断人和。 (猜一猜)	",
				"	 我用自己的方式爱你	", "爱情", "无");
		Riddle riddle175 = new Riddle("	有两只蜜蜂很相爱,后来母蜜蜂却嫁给了蜘蛛为什么 (猜一猜)	",
				"	 因为这只母蜜蜂爱上网	", "爱情", "无");
		Riddle riddle176 = new Riddle("	热恋中的情侣(打一花卉) 	", "	 鹤望兰	", "爱情", "无");
		Riddle riddle177 = new Riddle("	恋(打一生物用语) 	", "	 半变态	", "爱情", "无");
		Riddle riddle178 = new Riddle("	心心相印(打二字常用语) 	", "	 体贴	", "爱情", "无");
		Riddle riddle179 = new Riddle("	黄昏恋和大龄剩女的恋爱史(打一电视剧) 	", "	 《我和老妈一起嫁》	",
				"爱情", "无");
		Riddle riddle180 = new Riddle("	麻辣情感小夫妻，都市宝宝惹争议(打一热门电影) 	",
				"	 《小儿难养》	", "爱情", "无");
		Riddle riddle181 = new Riddle("	绑架我爱上你(打一电影) 	", "	 《边境风云》	", "爱情", "无");
		Riddle riddle182 = new Riddle("	嫁个傻丈夫,常常发牢骚(打四字成语) 	", "	 痴男怨女	", "爱情",
				"无");
		Riddle riddle183 = new Riddle("	洁白的爱(打一花卉) 	", "	 葱兰	", "爱情", "无");
		Riddle riddle184 = new Riddle("	在早餐时从来不吃的是什么？ (猜一猜)	", "	 午餐和晚餐	",
				"爱情", "无");
		Riddle riddle185 = new Riddle("	糊涂姻缘(打一通假字谜) 	", "	 昏通婚	", "爱情", "无");
		Riddle riddle186 = new Riddle("	月露良宵拜魁星，老牛庆生也不迟(打一节日) 	", "	 七夕节	",
				"爱情", "无");
		Riddle riddle187 = new Riddle("	大律师与五位妻子间的爱恨情仇(打一热门电视剧) 	",
				"	 《名媛望族》	", "爱情", "无");
		Riddle riddle188 = new Riddle("	树儿睁开眼，小子屋下眠，良心缺一点，日落残兔边。 (猜一猜)	",
				"	 相见恨晚	", "爱情", "无");
		Riddle riddle189 = new Riddle("	折磨人的东西，男女为之所困(打一名词) 	", "	 爱情	", "爱情",
				"无");
		Riddle riddle190 = new Riddle("	一只蚂蚁居然从四川爬到了东京，可能吗？ (猜一猜)	",
				"	 可能从地图上	", "爱情", "无");
		Riddle riddle191 = new Riddle("	在绝境中萌生的爱情(打一电影) 	", "	 《最爱》	", "爱情",
				"无");
		Riddle riddle192 = new Riddle("	什么东西闭着眼睛可以看到睁着研究却看不到? (猜一猜)	", "	 梦	",
				"爱情", "无");
		Riddle riddle193 = new Riddle("	此情可待成追忆，只是当时已惘然(打一歌曲) 	",
				"	 《有多少爱可以重来》	", "爱情", "无");
		Riddle riddle194 = new Riddle("	浪漫真情(打一花卉) 	", "	 紫玫瑰	", "爱情", "无");
		Riddle riddle195 = new Riddle(
				"	天鹅飞去鸟不归，怀念昔日空费心，云开月下双匕影，水流几处又相逢，日落月出人倚月，单身贵族尔相随。 (猜一猜)	",
				"	 我不能没有你	", "爱情", "无");
		Riddle riddle196 = new Riddle("	带上真心去相亲，闲人免谈(打一综艺节目) 	", "	 非诚勿扰	",
				"爱情", "无");
		Riddle riddle197 = new Riddle(
				"	古树撑天枝难觅，怀抱可怜却无心，赵国有妃不是女，鹅毛轻飘鸟不见，远去不想囊羞涩，受尽苦难又换友，自称有人伴君旁 (猜一猜)	",
				"	 对不起我还爱你	", "爱情", "无");
		Riddle riddle198 = new Riddle("	要爱爱我(打一国外节日) 	", "	 圣诞节	", "爱情", "无");
		Riddle riddle199 = new Riddle("	为什么女人说男人都不是好东西？ (猜一猜)	",
				"	 因为她之前没有遇到过好男人	", "爱情", "无");
		Riddle riddle200 = new Riddle("	十八岁的姑娘被锁深宫 (猜一个字)	",
				"	 困 (十八是木，深宫四面围墙所以是口)	", "爱情", "无");
		Riddle riddle201 = new Riddle("	最吉祥的男艺人是谁？ (猜一猜)	", "	 陶喆	", "爱情", "无");
		Riddle riddle202 = new Riddle("	白衣天使爱上卧底警察(打一电视剧) 	", "	 《暗线》	", "爱情",
				"无");
		Riddle riddle203 = new Riddle("	小两口本是同林鸟(打一交通用语) 	", "	 禁鸣	", "爱情", "无");
		Riddle riddle204 = new Riddle("	请不要忘记我(打一花卉) 	", "	 水仙花(土耳其花语)	", "爱情",
				"无");
		Riddle riddle205 = new Riddle("	我要与你过一辈子(打一花卉) 	", "	 粉红蔷薇(花语)	", "爱情",
				"无");
		Riddle riddle206 = new Riddle("	小刚能4个小时不眨眼，为什么？ (猜一猜)	", "	 因为他睡着了	",
				"爱情", "无");
		Riddle riddle207 = new Riddle("	我想有个家(打四字常用语) 	", "	 希望所在	", "爱情", "无");
		Riddle riddle208 = new Riddle(
				"	淮海又见水退时，双人换走阻碍石，月顶右手不见口，青年男女树心旁，世上何物最懂爱  (猜一猜)	", "	 难得有情人	",
				"爱情", "无");
		Riddle riddle209 = new Riddle("	地狱里打官司 (打一成语) 	", "	 死对头	", "爱情", "无");
		Riddle riddle210 = new Riddle("	有风不动无风动，不动无风动有风(猜一物) 	", "	 扇子	", "爱情",
				"无");
		Riddle riddle211 = new Riddle("	守门员(猜一字) 	", "	 闪	", "爱情", "无");
		Riddle riddle212 = new Riddle("	大明的英语非常好，可是有些老外却听不懂，为什么？ (猜一猜)	",
				"	 日本人听他说英语	", "爱情", "无");
		Riddle riddle213 = new Riddle("	窈窕淑女，暗自求爱(打一奥运明星) 	",
				"	 邱波(“暗自求爱”即为“暗送秋波”)	", "爱情", "无");
		Riddle riddle214 = new Riddle("	无告白不恋爱(打一电视剧) 	", "	 《爱情是从告白开始的》	",
				"爱情", "无");
		Riddle riddle215 = new Riddle("	褪去的爱(打一花卉) 	", "	 黄玫瑰	", "爱情", "无");
		Riddle riddle216 = new Riddle("	爱的誓言(打一花卉) 	", "	 粉蔷薇	", "爱情", "无");
		Riddle riddle217 = new Riddle("	哑巴谈恋爱(打一歌名) 	", "	 爱你在心口难开	", "爱情", "无");
		Riddle riddle218 = new Riddle("	罗马分手(打三字常用语) 	", "	 别在意	", "爱情", "无");
		Riddle riddle219 = new Riddle(
				"	天鹅飞去鸟不归， 良字无头双人配； 受字中间多两笔(双木非林心相连)， 人尔结合就是自己。 (猜一猜)	",
				"	 我很爱<想>你	", "爱情", "无");
		Riddle riddle220 = new Riddle("	半夜有行舟探骊格 (猜一猜)	", "	 泊人一·时迁	", "爱情",
				"无");
		Riddle riddle221 = new Riddle("	小小绒球随风飘，深情爱意停不了(打一花卉) 	", "	 蒲公英	",
				"爱情", "无");
		Riddle riddle222 = new Riddle(
				"	古树撑天枝难觅，怀抱可怜却无心，赵国有妃不是女，鹅毛轻飘鸟不见，远去不想囊羞涩，受尽苦难又换友，自称有人伴君旁。(猜一猜) 	",
				"	 对不起我还爱你	", "爱情", "无");
		Riddle riddle223 = new Riddle("	什么数字最听话呢？ (猜一猜)	", "	 100，百依百顺	", "爱情",
				"无");
		Riddle riddle224 = new Riddle("	弑夫女犯与情场骗子的闪恋(打一热门电影) 	", "	 《晚秋》	",
				"爱情", "无");
		Riddle riddle225 = new Riddle(
				"	春雨季，梧桐树上结丝绸；夏日凉，两人阵中称英雄；秋风起，鸿雁传音数千里；冬雪飘，美女为何露半腰。 (猜一猜)	",
				"	 绝对重要	", "爱情", "无");
		Riddle riddle226 = new Riddle("	帮妻子学文化(打一教育用语) 	", "	 教室	", "爱情", "无");
		Riddle riddle227 = new Riddle("	30岁女教师和17岁高中生的危险恋情(打一电影) 	", "	 《智齿》	",
				"爱情", "无");
		Riddle riddle228 = new Riddle("	小华的男朋友说小华吃的少小华却生气了为什么？(猜一猜)	",
				"	 他说小华肚量小，小华当然生气了	", "爱情", "无");
		Riddle riddle229 = new Riddle("	热恋中的情侣(打一诗句) 	", "	 相看两不厌	", "爱情", "无");
		Riddle riddle230 = new Riddle("	你知道聪明和傻子的区别吗？(猜一猜) 	", "	 傻子提问聪明	",
				"爱情", "无");
		Riddle riddle231 = new Riddle("	那种人一年只工作一次？ (猜一猜)	", "	 圣诞老人	", "爱情",
				"无");
		Riddle riddle232 = new Riddle("	辰时下雨 地动山摇  (打一个字) 	", "	 震	", "爱情", "无");
		Riddle riddle233 = new Riddle(
				"	春雨季梧桐树上结丝绸；夏日凉两人阵中称英雄；秋风起鸿雁传音数千里；冬雪飘美女为何露半腰。 (猜一猜) 	",
				"	 绝对重要	", "爱情", "无");
		Riddle riddle234 = new Riddle("	刘烨胡军断背之恋(打一电影) 	", "	 《蓝宇》	", "爱情", "无");
		Riddle riddle235 = new Riddle("	女人是男人的一半(打一数学用语) 	", "	 公倍数	", "爱情",
				"无");
		Riddle riddle236 = new Riddle("	犬夜叉(打一个现在名词) 	", "	 人妖	", "爱情", "无");
		Riddle riddle237 = new Riddle(
				"	纸上写着某个命令，但是，看到此文字的人绝对不能宣读命令。那么，纸上写的是什么呢？ 	", "	 不要念出此	",
				"爱情", "无");
		Riddle riddle238 = new Riddle("	我心载着你，不上你的船，已在不言中，双人日下行，燕子离鹅去，马口解猜疑	",
				"	 您还记得我吗	", "爱情", "无");
		Riddle riddle239 = new Riddle("	三角恋(打一热门电影) 	", "	 2012最新电影：《危险关系》	",
				"爱情", "无");
		Riddle riddle240 = new Riddle("	泰山拉着树腾在丛林间穿梭时，为什么要扯着喉咙大叫？ (猜一猜)	",
				"	 他怕不相似的猴子迎面扑过来	", "爱情", "无");

		Riddle riddle241 = new Riddle("	句号(打四字常用语) 	", "	 可圈可点	", "搞笑", "无");
		Riddle riddle242 = new Riddle("	拉出去的屎(打一成语) 	", "	 有去无回	", "搞笑", "无");
		Riddle riddle243 = new Riddle("	房屋,宫殿,岩洞,大厦,牛棚,那个词与众不同？ 	",
				"	 岩洞，其它都是人工的	", "搞笑", "无");
		Riddle riddle244 = new Riddle("	八戒的屁股(打一明星) 	", "	 朱茵	", "搞笑", "无");
		Riddle riddle245 = new Riddle("	只哼哼曲调,不唱歌词,为什么？(打一女明星) 	",
				"	 吴佩慈(无配词)	", "搞笑", "无");
		Riddle riddle246 = new Riddle("	兔子路边走(打一城市) 	", "	 蚌埠	", "搞笑", "无");
		Riddle riddle247 = new Riddle("	天黑了(打一歌名) 	", "	 《夜夜夜夜》	", "搞笑", "无");
		Riddle riddle248 = new Riddle("	增肥特效药打一物品 	", "	 肥料	", "搞笑", "无");
		Riddle riddle249 = new Riddle("	便秘的原因(打一好莱坞明星) 	", "	 史泰龙	", "搞笑", "无");
		Riddle riddle250 = new Riddle("	小刚住一楼为什么还有恐高症？ 	", "	 他怕他妈妈，因为他妈妈姓高	",
				"搞笑", "无");
		Riddle riddle251 = new Riddle(
				"	红冠黑嘴白衣裳，双腿细瘦走路晃，漫步水中捕鱼虾，凌空展翅能飞翔。(打一动物)	", "	 鹤	", "搞笑", "无");
		Riddle riddle252 = new Riddle("	屎壳郎糟蹋粮食(打一明星) 	", "	 费翔	", "搞笑", "无");
		Riddle riddle253 = new Riddle("	下围棋(打一食品) 	", "	 包子	", "搞笑", "无");
		Riddle riddle254 = new Riddle("	伯父(打称谓二) 	", "	 爸爸、的哥	", "搞笑", "无");
		Riddle riddle255 = new Riddle("	男模走猫步(打一军事用语) 	", "	 两面夹击	", "搞笑", "无");
		Riddle riddle256 = new Riddle("	玉环飞燕皆尘土(打一电视剧名) 	", "	 《绝代双骄》	", "搞笑",
				"无");
		Riddle riddle257 = new Riddle("	狮子，老虎，骡子，鹿在大漠里面行走谁渴？ (猜一猜)	",
				"	 鹿渴(look)	", "搞笑", "无");
		Riddle riddle258 = new Riddle("	怎样才能防止第二次感冒？ (猜一猜)	",
				"	 得第二次感冒后就不会再有第二次感冒了	", "搞笑", "无");
		Riddle riddle259 = new Riddle("	九只鸟,猜一个字 	", "	 鸠	", "搞笑", "无");
		Riddle riddle260 = new Riddle("	女儿经(打一花卉) 	", "	 月月红	", "搞笑", "无");
		Riddle riddle261 = new Riddle("	做什么事会身不由己？ (猜一猜)	", "	 做梦	", "搞笑", "无");
		Riddle riddle262 = new Riddle("	白弟弟，黑哥哥，排排坐，爱唱歌，丁冬，丁冬，快乐多 (猜一猜)	",
				"	 钢琴	", "搞笑", "无");
		Riddle riddle263 = new Riddle("	一个风箱真奇怪，一拉它就唱起来。 (打一乐器) 	", "	 手风琴	",
				"搞笑", "无");
		Riddle riddle264 = new Riddle("	别抖，稳住了再开枪。(八字口语) 	", "	 不要动不动就发火	",
				"搞笑", "无");
		Riddle riddle265 = new Riddle("	耶稣是哪里的人？(猜一猜)	", "	 天国	", "搞笑", "无");
		Riddle riddle266 = new Riddle("	忐(打三字常用语) 	", "	 上进心	", "搞笑", "无");
		Riddle riddle267 = new Riddle("	否(打一教育用语) 	", "	 不及格	", "搞笑", "无");
		Riddle riddle268 = new Riddle("	二八佳人  (猜一个字) 	", "	 妙	", "搞笑", "无");
		Riddle riddle269 = new Riddle("	君王从此不早朝(打一食品名) 	", "	 艾窝窝	", "搞笑", "无");
		Riddle riddle270 = new Riddle("	三个金鑫，三个水叫淼，三个人叫众，那么三个鬼应该叫什么? (猜一猜)	",
				"	 叫救命	", "搞笑", "无");
		Riddle riddle271 = new Riddle("	一个太监(打一水果) 	", "	 梅子(没子：没有儿子)	", "搞笑",
				"无");
		Riddle riddle272 = new Riddle("	星姐选举(打一国家) 	", "	 以色列	", "搞笑", "无");
		Riddle riddle273 = new Riddle("	禁止放牲口出去吃草(打一诗人) 	", "	 杜牧	", "搞笑", "无");
		Riddle riddle274 = new Riddle("	喝三口水解酸(打一饮品) 	", "	 酒	", "搞笑", "无");
		Riddle riddle275 = new Riddle(
				"	小飞贼，水里生，干坏事，狠又凶，偷偷摸摸吸人血，还要嗡嗡叫几声。(打一动物)	", "	 蚊子	", "搞笑", "无");
		Riddle riddle276 = new Riddle("	火辣辣的手(打一花卉) 	", "	 红掌(别称“火鹤花、安祖花”)	",
				"搞笑", "无");
		Riddle riddle277 = new Riddle("	号称山大王，树干冲天长，叶儿尖似针，造屋好做梁。(打一植物) 	",
				"	 杉树	", "搞笑", "无");
		Riddle riddle278 = new Riddle(
				"	天南地北都能住，春风给我把辫梳，溪畔湖旁搭凉棚，能撒雪花当空舞。(打一植物)	", "	 柳树	", "搞笑", "无");
		Riddle riddle279 = new Riddle("	双胞胎老大(打一称呼) 	", "	 先生	", "搞笑", "无");
		Riddle riddle280 = new Riddle("	琵琶舞(打一体育用语) 	", "	 弹跳	", "搞笑", "无");
		Riddle riddle281 = new Riddle("	袁珊珊票房赢了杨幂(打一热门人物) 	", "	 袁厉害	", "搞笑",
				"无");
		Riddle riddle282 = new Riddle("	走进死巷子(打一物理名词) 	", "	 断路	", "搞笑", "无");
		Riddle riddle283 = new Riddle("	冻猪头(打一食品名) 	", "	 冷狗	", "搞笑", "无");
		Riddle riddle284 = new Riddle("	习惯性流产(打一称谓) 	", "	 老先生	", "搞笑", "无");
		Riddle riddle285 = new Riddle("	瞎丑(打一乳制品品牌) 	", "	 蒙牛	", "搞笑", "无");
		Riddle riddle286 = new Riddle("	一只手能干的事情(打一热门小说) 	",
				"	 《遮天》(因为“只手遮天”)	", "搞笑", "无");
		Riddle riddle287 = new Riddle("	貌赛潘安(打一港台男明星) 	", "	 潘玮柏	", "搞笑", "无");
		Riddle riddle288 = new Riddle("	十二点  (猜一个字) 	", "	 斗	", "搞笑", "无");
		Riddle riddle289 = new Riddle("	四根头发掉了一根(打一作家) 	", "	 三毛	", "搞笑", "无");
		Riddle riddle290 = new Riddle("	天地会(打一出版用语) 	", "	 上下集	", "搞笑", "无");
		Riddle riddle291 = new Riddle("	男人也能怀孕(打一电影) 	", "	 《肚爸爸生子记》	", "搞笑",
				"无");
		Riddle riddle292 = new Riddle("	死在地里的小红薯(打一离合字) 	", "	 土里埋	", "搞笑", "无");
		Riddle riddle293 = new Riddle("	兜售小三(打一美国城市) 	", "	 迈阿密(卖，阿密：小三(小秘))	",
				"搞笑", "无");
		Riddle riddle294 = new Riddle("	你知道最有价值的布是什么吗？(猜一猜) 	", "	 瀑布	", "搞笑",
				"无");
		Riddle riddle295 = new Riddle("	用被子盖住屁股(打一个城市名字) 	",
				"	 保定(抱定 ，定徐州话屁股)	", "搞笑", "无");
		Riddle riddle296 = new Riddle("	手捧一把棍(打一复姓) 	", "	 端木	", "搞笑", "无");
		Riddle riddle297 = new Riddle("	把尿喝光不撑肚，妈妈有它少烦恼(打一生活用品) 	", "	 尿不湿	",
				"搞笑", "无");
		Riddle riddle298 = new Riddle("	什么时候，时代广场的大钟会响13下？ (猜一猜)	",
				"	 该修理的时候	", "搞笑", "无");
		Riddle riddle299 = new Riddle("	宾客尽脱帽，洒泪来反思(打一音乐人，2字) 	", "	 洛兵	",
				"搞笑", "无");
		Riddle riddle300 = new Riddle("	二师兄的嗓门(打一明星) 	", "	 朱茵(猪音)	", "搞笑", "无");
		Riddle riddle301 = new Riddle("	八戒头上一滩血(打一花卉) 	", "	 朱顶红(猪顶红)	", "搞笑",
				"无");
		Riddle riddle302 = new Riddle(
				"	百货公司里，有个秃头的推销员，正在促销生发水，你知道他为什么自己不用生发水呢？(猜一猜)	",
				"	 他想让大家知道秃头有多难看	", "搞笑", "无");
		Riddle riddle303 = new Riddle("	鸡蛋(打一歌名) 	", "	 心太软	", "搞笑", "无");
		Riddle riddle304 = new Riddle("	月(打一教育用语) 	", "	 期末	", "搞笑", "无");
		Riddle riddle305 = new Riddle("	满足(打一物) 	", "	 袜子	", "搞笑", "无");
		Riddle riddle306 = new Riddle("	鼠年出生(打一人名) 	", "	 子产	", "搞笑", "无");
		Riddle riddle307 = new Riddle("	女人穿裤子(打一车名) 	", "	 蓝(拦)鸟	", "搞笑", "无");
		Riddle riddle308 = new Riddle("	都来太阳底下穿袄子(打一物理名词) 	", "	 比热	", "搞笑",
				"无");
		Riddle riddle309 = new Riddle("	你无知到似母猪(打一明星) 	", "	 李宇春	", "搞笑", "无");
		Riddle riddle310 = new Riddle("	电世界(打一食品) 	", "	 麻球	", "搞笑", "无");
		Riddle riddle311 = new Riddle("	黑色的车(打一名词) 	", "	 黑车	", "搞笑", "无");
		Riddle riddle312 = new Riddle("	嫦娥在哪里(打一歌名) 	", "	 月亮之上	", "搞笑", "无");
		Riddle riddle313 = new Riddle("	一架坐满人的飞机坠落，却没有人受伤。为什么？(猜一猜) 	",
				"	 因为都死了	", "搞笑", "无");
		Riddle riddle314 = new Riddle(
				"	海南宝岛是我家，不怕风吹和雨打，四季棉衣不离身，肚里有肉又有茶。(打一植物)	", "	 椰子	", "搞笑", "无");
		Riddle riddle315 = new Riddle("	男女关系不清不白(打一歌曲) 	", "	 《暧昧》——杨丞琳	",
				"搞笑", "无");
		Riddle riddle316 = new Riddle("	曲从其命(打一曲艺用语) 	", "	 绕口令	", "搞笑", "无");
		Riddle riddle317 = new Riddle("	你在什么时候喜欢喝汽水？ (猜一猜)	",
				"	 在孤单的时候(当你孤单你会想汽水)	", "搞笑", "无");
		Riddle riddle318 = new Riddle("	在家千般好(打一人事用语) 	", "	 出差	", "搞笑", "无");
		Riddle riddle319 = new Riddle("	30岁的女人(打一成语) 	", "	 如狼似虎	", "搞笑", "无");
		Riddle riddle320 = new Riddle("	一个太监(打一水果) 	", "	 梅子(没子：没有儿子)	", "搞笑",
				"无");

		Riddle riddle321 = new Riddle("	纸绢糊在竹篾上，飞上天空微荡漾(打一玩具) 	", "	 风筝	",
				"儿童", "无");
		Riddle riddle322 = new Riddle("	外披麻衣裟，内穿红大褂。胖子里面藏，地下自安家(打一农作物) 	",
				"	 花生	", "儿童", "无");
		Riddle riddle323 = new Riddle("	被马来西亚视为不吉祥的花(打一花卉) 	",
				"	 蝙蝠花(因为不讨喜的外型)	", "儿童", "无");
		Riddle riddle324 = new Riddle("	穿奇装异服的人最让谁头疼？ (猜一猜)	", "	 裁缝	", "儿童",
				"无");
		Riddle riddle325 = new Riddle("	儿童节合唱(打一离合字) 	", "	 六一日音	", "儿童", "无");
		Riddle riddle326 = new Riddle("	儿童节(打一离合字) 	", "	 自小省	", "儿童", "无");
		Riddle riddle327 = new Riddle("	外表似荔枝，却带长软刺(打一水果) 	", "	 红毛丹	", "儿童",
				"无");
		Riddle riddle328 = new Riddle("	八十万禁军教头，人称豹子头(打一水浒传人物名) 	", "	 林冲	",
				"儿童", "无");
		Riddle riddle329 = new Riddle("	我有一个好帮手，书本文具帮我拿，每天跟我上学校(打一物) 	",
				"	 书包	", "儿童", "无");
		Riddle riddle330 = new Riddle("	长生果儿穿麻衣，植物肉儿白身体(打一食物) 	", "	 花生	",
				"儿童", "无");
		Riddle riddle331 = new Riddle("	样子像小船，角儿两头翘，骨头在外面，肉儿里头包。(打一植物) 	",
				"	 菱角	", "儿童", "无");
		Riddle riddle332 = new Riddle("	一根根，两成双，三个指头夹住它(打一生活用品) 	", "	 筷子	",
				"儿童", "无");
		Riddle riddle333 = new Riddle("	头上毛发像大便，好吃懒做大肥羊。(打一动画角色) 	", "	 懒羊羊	",
				"儿童", "无");
		Riddle riddle334 = new Riddle("	调皮小萝莉(打一网站名) 	", "	 淘宝网(淘气的宝宝)	", "儿童",
				"无");
		Riddle riddle335 = new Riddle("	八仙过海到此地，伏羲女娲居其中(打一地名) 	",
				"	 蓬莱(烟台县级市)	", "儿童", "无");
		Riddle riddle336 = new Riddle("	一堆黄豆豆,磨碎熬白汤,凝成大块块,吃来嫩又香(打一食品) 	",
				"	 豆腐	", "儿童", "无");
		Riddle riddle337 = new Riddle("	两只小小船，陪你在身边，白天到处走，晚上停床边(打一日用品) 	",
				"	 鞋	", "儿童", "无");
		Riddle riddle338 = new Riddle("	多出一半(打一个字) 	", "	 岁	", "儿童", "无");
		Riddle riddle339 = new Riddle("	有个大肚皮,真是怪脾气,张着大嘴街上站,专吃各种脏东西(打一日用品) 	",
				"	 垃圾桶	", "儿童", "无");
		Riddle riddle340 = new Riddle("	鱼为什么生活在水里？ (猜一猜)	", "	 因为猫在岸上	", "儿童",
				"无");
		Riddle riddle341 = new Riddle("	方方一块布,布上长疙瘩，天天去洗脸，说我好娃娃(打一日用品) 	",
				"	 毛巾	", "儿童", "无");
		Riddle riddle342 = new Riddle("	鹿儿雪橇响叮当，小小礼物娃娃抢(打一国外节日) 	", "	 圣诞节	",
				"儿童", "无");
		Riddle riddle343 = new Riddle("	灯暗秋寒明月隐(打一花名) 	", "	 丁香	", "儿童", "无");
		Riddle riddle344 = new Riddle("	湿、绿、岸、春、透组成一句话，哈哈 (猜一猜)	",
				"	 岸湿透春绿，俺是头蠢驴！	", "儿童", "无");
		Riddle riddle345 = new Riddle("	小明考了96分小华比小明多一点小华考了多少分 (猜一猜)	",
				"	 9.6分	", "儿童", "无");
		Riddle riddle346 = new Riddle(
				"	比牛大，吃嫩草，头上长只弯弯角。老虎见它让三分，力大无穷脾气暴。( 打一动物)	", "	 犀牛	", "儿童", "无");
		Riddle riddle347 = new Riddle(
				"	身细头尖鼻子大，一根线儿拴住它， 帮助妈妈缝衣裳，帮助姐姐来绣花。 (猜一猜)	", "	 针	", "儿童", "无");
		Riddle riddle348 = new Riddle("	远看像雪花，海水中有它。用途非常广，做菜顶呱呱(打一调味品) 	",
				"	 盐	", "儿童", "无");
		Riddle riddle349 = new Riddle("	头裹布巾的小女孩(打一儿歌) 	", "	 《小红帽》	", "儿童",
				"无");
		Riddle riddle350 = new Riddle("	红皮红瓤瓤,酸甜有营养,生吃赛水果,蛋炒味道香(打一蔬菜) 	",
				"	 西红柿	", "儿童", "无");
		Riddle riddle351 = new Riddle("	半推半就 (猜一字) 	", "	 掠	", "儿童", "无");
		Riddle riddle352 = new Riddle("	七个男孩智斗美人儿(打一动画片) 	", "	 《葫芦娃》	", "儿童",
				"无");
		Riddle riddle353 = new Riddle("	一点一横长，方砖顶房梁。大口张着嘴，小口里面藏 	(猜一猜)",
				"	 高	", "儿童", "无");
		Riddle riddle354 = new Riddle("	死去何所知打酒名一 (猜一猜)	", "	 百年糊涂	", "儿童", "无");
		Riddle riddle355 = new Riddle("	远看像张弓，立在河当中。上面车马过，底下轮船游。(打一交通设施) 	",
				"	 桥	", "儿童", "无");
		Riddle riddle356 = new Riddle("	戴绿帽披绿衫,白天黑夜邮局站,书信往来它传递，准确及时又安全(打一物品)	",
				"	 邮筒	", "儿童", "无");
		Riddle riddle357 = new Riddle(
				"	默默站在马路边,没到夜晚亮闪闪,汽车行人从旁过，尽职尽责保安全(打一交通设施)	", "	 路灯	", "儿童", "无");
		Riddle riddle358 = new Riddle("	轻轻跑过水面,水面浪花飞溅,树叶哗哗鼓掌,响声连成一片(打一自然现象) 	",
				"	 风	", "儿童", "无");
		Riddle riddle359 = new Riddle("	身披黄袍营养高，热带果王味道好(打一水果) 	", "	 芒果	",
				"儿童", "无");
		Riddle riddle360 = new Riddle("	小时开白花,大时结黄果,皮粗果肉细,吃了能败火(打一水果) 	",
				"	 梨	", "儿童", "无");
		Riddle riddle361 = new Riddle("	一只像耳插在后，一条象鼻长在前，肚子像孕妇，有水往里灌 	",
				"	 水壶	", "儿童", "无");
		Riddle riddle362 = new Riddle("	木头上面来回走,光有牙,没有口(打一物) 	", "	 锯子	", "儿童",
				"无");
		Riddle riddle363 = new Riddle("	冬天蟠龙卧，夏天枝叶开，龙须往上长，珍珠往下排。(打一植物) 	",
				"	 葡萄	", "儿童", "无");
		Riddle riddle364 = new Riddle("	一个老爷爷，推他他不倒，总爱把头摇(打一物) 	", "	 不倒翁	",
				"儿童", "无");
		Riddle riddle365 = new Riddle("	壳儿硬，门板隔，里面藏个皱脸老婆婆(打一坚果) 	", "	 核桃	",
				"儿童", "无");
		Riddle riddle366 = new Riddle("	平安的果子 (打一水果) 	", "	 苹果	", "儿童", "无");
		Riddle riddle367 = new Riddle(
				"	小小银针本领高，扎了脑袋扎双脚。外国朋友也欢迎，针到病除乐陶陶。(打一治病方法) 	", "	 针灸	", "儿童",
				"无");
		Riddle riddle368 = new Riddle("	四四方方像块糖，写错字儿用它擦(打一学习用品) 	", "	 橡皮擦	",
				"儿童", "无");
		Riddle riddle369 = new Riddle("	身材细又长，头上长着毛，每天把它用，做个好宝宝(打一日用品) 	",
				"	 牙刷	", "儿童", "无");
		Riddle riddle370 = new Riddle("	弄瓦之徵  (猜一个字) 	", "	 姚	", "儿童", "无");
		Riddle riddle371 = new Riddle("	五人住一起，个头也不齐(打一人体部位) 	", "	 手指	", "儿童",
				"无");
		Riddle riddle372 = new Riddle("	那拔(打一水果) 	", "	 番石榴(俗称“那拔”)	", "儿童",
				"无");
		Riddle riddle373 = new Riddle("	四四方方豆腐块,每天早起亲脸蛋(打一物) 	", "	 毛巾	", "儿童",
				"无");
		Riddle riddle374 = new Riddle("	吃进青青草,挤出甜甜水,谢谢牛妈妈,让我快长大(打一食品) 	",
				"	 牛奶	", "儿童", "无");
		Riddle riddle375 = new Riddle("	身材纤细披绿袍，切碎包饺味儿好(打一蔬菜) 	", "	 芹菜	",
				"儿童", "无");
		Riddle riddle376 = new Riddle("	圆圆浆果色泽艳，草龙珠儿味道甜(打一水果) 	", "	 葡萄	",
				"儿童", "无");
		Riddle riddle377 = new Riddle("	小花儿，像喇叭，公鸡打鸣就开放(打一花类) 	", "	 牵牛花	",
				"儿童", "无");
		Riddle riddle378 = new Riddle("	一座小楼房,有门没有窗。打开看一看,全是花衣裳(打一家具) 	",
				"	 衣柜	", "儿童", "无");
		Riddle riddle379 = new Riddle("	一个绿娃娃，肚里水汪汪，若是剖开看，红汁往外淌(打一水果) 	",
				"	 西瓜	", "儿童", "无");
		Riddle riddle380 = new Riddle("	没有孩子却当娘，欢欢喜喜红线牵。 (打一角色) 	", "	 红娘	",
				"儿童", "无");
		Riddle riddle381 = new Riddle(
				"	小小哥儿鼻子长，肚子还能把水装。它见花儿流眼泪，花儿见它笑哈哈。(打一物)	", "	 洒水壶	", "儿童", "无");
		Riddle riddle382 = new Riddle(
				"	一头青来一头白，胡子还在地里埋。头上顶朵小白花，烹饪调味品质佳。(打一蔬菜)	", "	 葱	", "儿童", "无");
		Riddle riddle383 = new Riddle("	毛茸茸像羽毛，大风一吹到处飞(打一植物) 	", "	 蒲公英	",
				"儿童", "无");
		Riddle riddle384 = new Riddle("	别看不会说话,无脚能行千里,先报一路平安,话都装在肚里(打一日用品) 	",
				"	 信	", "儿童", "无");
		Riddle riddle385 = new Riddle("	白雪公主出自那本书 (猜一猜)	", "	 格林童话	", "儿童", "无");
		Riddle riddle386 = new Riddle("	在中国被人唱的最多的歌是什么歌？ (猜一猜)	", "	 国歌	",
				"儿童", "无");
		Riddle riddle387 = new Riddle("	五颜六色真漂亮，喜庆时节都用它(打一玩具) 	", "	 气球	",
				"儿童", "无");
		Riddle riddle388 = new Riddle("	魔法少女的珍贵友情(打一动画片) 	", "	 《彩虹心石》	", "儿童",
				"无");
		Riddle riddle389 = new Riddle("	小兰并没有病，为什么她吃一口吐一口？ 	", "	 她在吃瓜子	",
				"儿童", "无");
		Riddle riddle390 = new Riddle("	披黄衣，不用剥，酸眯眼，泡泡水(打一水果) 	", "	 柠檬	",
				"儿童", "无");
		Riddle riddle391 = new Riddle("	胖娃娃，嘴真大，不管吃啥都要它(打一生活用品) 	", "	 碗	",
				"儿童", "无");
		Riddle riddle392 = new Riddle(
				"	就怕热不怕冷,像玻璃亮晶晶,数九寒天河里有,春暖花开难找寻(打一自然物) 	", "	 冰	", "儿童", "无");
		Riddle riddle393 = new Riddle("	热带果王(打一水果) 	", "	 芒果	", "儿童", "无");
		Riddle riddle394 = new Riddle("	丘比特之剑 -的一中药 	",
				"	 穿心莲。了(箭把两颗心穿透连在一起)	", "儿童", "无");
		Riddle riddle395 = new Riddle("	嘴巴大又大,从来不讲话。盛汤又装饭,落地会摔烂(打一物) 	",
				"	 碗	", "儿童", "无");
		Riddle riddle396 = new Riddle("	人脱衣服，它穿衣服，人脱帽子，它戴帽子(打一物品) 	", "	 衣帽架	",
				"儿童", "无");
		Riddle riddle397 = new Riddle("	洁白如玉有大口，装菜盛饭好帮手(打一生活用品) 	", "	 碗	",
				"儿童", "无");
		Riddle riddle398 = new Riddle(
				"	一匹马儿两人骑，这边高来那边低， 虽然马儿不会跑，两人骑着笑嘻嘻。 (打一玩具)	", "	 跷跷板	", "儿童",
				"无");
		Riddle riddle399 = new Riddle(
				"	IQ博士智商高，机器娃娃也来造；近视娃娃个头小，最爱说着哦呦呦(打一动漫人物)	", "	 阿拉蕾	", "儿童",
				"无");
		Riddle riddle400 = new Riddle("	你有两只眼,它有两只眼,剧场来看戏,它能看得远(打一科技产品) 	",
				"	 望远镜	", "儿童", "无");

		Riddle riddle401 = new Riddle("	卡罗拉(打一英文单词) 	", "	 corolla花冠	", "英语",
				"无");
		Riddle riddle402 = new Riddle("	怕失去(打一英文单词) 	", "	 parsimonious	",
				"英语", "无");
		Riddle riddle403 = new Riddle(
				"	What has hands but no feet, a face but no eyes, tells but not talk? (猜一猜)",
				"	 alarm clock 闹钟	", "英语", "无");
		Riddle riddle404 = new Riddle("	台湾姐妹明星(打一英文字) 	", "	 s	", "英语", "无");
		Riddle riddle405 = new Riddle(
				"	Why don't you advertise for your lost dog? (猜一猜)	",
				"	 He can't read.	", "英语", "无");
		Riddle riddle406 = new Riddle(
				"	Why is the library the highest building? (猜一猜)	",
				"	 It has the most stories.	", "英语", "无");
		Riddle riddle407 = new Riddle(
				"	When the boy fell into the water, what's the first thing he did? (猜一猜)	",
				"	 He got wet first of all.	", "英语", "无");
		Riddle riddle408 = new Riddle("	什么英文字母让人们喜欢听而且听的人最多？ (猜一猜)	", "	 CD	",
				"英语", "无");
		Riddle riddle409 = new Riddle(
				"	话说26个字母在外太空，ABC跑地球来了，外太空还有多少个字母	 (猜一猜)", "	 20个，他们坐UFO下来的！	",
				"英语", "无");
		Riddle riddle410 = new Riddle(
				"	What animal wears big black glasses on its face? (猜一猜)	",
				"	 Panda熊猫	", "英语", "无");
		Riddle riddle411 = new Riddle("	阴塞(打一英语单词) 	", "	 inside	", "英语", "无");
		Riddle riddle412 = new Riddle(
				"	What always goes up and never goes down?(什么东西只升不降？)	",
				"	 Your age. (你的年龄)	", "英语", "无");
		Riddle riddle413 = new Riddle("	摸(打一英文单词) 	", "	 more(多，更多)	", "英语",
				"无");
		Riddle riddle414 = new Riddle("	C型的锁 (打一英文) 	", "	 clock.	", "英语", "无");
		Riddle riddle415 = new Riddle(
				"	How long can a goose stand on one leg？(多久能鹅单腿站立？) 	",
				"	 try it and see	", "英语", "无");
		Riddle riddle416 = new Riddle("	what month do soldiers hate (猜一猜)	",
				"	 March 3月	", "英语", "无");
		Riddle riddle417 = new Riddle("	俺靠(打一英文单词) 	",
				"	 uncle叔叔；伯父；舅父；姑父，姨父。	", "英语", "无");
		Riddle riddle418 = new Riddle(
				"	What kind of water should people drink in order to be healthy? (猜一猜)",
				"	 Drink well water.	", "英语", "无");
		Riddle riddle419 = new Riddle("	狗打毛宁(打一英文短语) 	",
				"	 Good morning!早上好！	", "英语", "无");
		Riddle riddle420 = new Riddle(
				"	Why did the farmer take his chicken to task?(农夫为什么训斥小鸡？)	",
				"	 Because they use foul language. 因为它们说脏话。	", "英语", "无");
		Riddle riddle421 = new Riddle("	一窝蚊子(打一英语单词) 	", "	 involving	", "英语",
				"无");
		Riddle riddle422 = new Riddle("	What has teeth but cannot eat? (猜一猜)",
				"	 A comb	", "英语", "无");
		Riddle riddle423 = new Riddle(
				"	Why do some old people never use glasses? (猜一猜)	",
				"	 They must prefer bottles to glasses.	", "英语", "无");
		Riddle riddle424 = new Riddle(
				"	What has four eyes but cannot see? (猜一猜)	", "	 密西西比州	", "英语",
				"无");
		Riddle riddle425 = new Riddle("	海味(打一英文单词) 	", "	 heavy	", "英语", "无");
		Riddle riddle426 = new Riddle("	皮蛋(打一英文) 	", "	 Q	", "英语", "无");
		Riddle riddle427 = new Riddle(
				"	Why is the inside of everything so mysterious?(为什么说凡事内部都神秘莫测？)	",
				"	 ecause you can never make them out. 因为你永远也不能弄懂它们。	", "英语",
				"无");
		Riddle riddle428 = new Riddle("	迪斯科(打一英文单词) 	", "	 disco	", "英语", "无");
		Riddle riddle429 = new Riddle(
				"	What comes after the letter 'A'? (猜一猜)	",
				"	 All the other letters.	", "英语", "无");
		Riddle riddle430 = new Riddle("	216个小时打一个字 	", "	 旭(因为216小时是九日)	",
				"英语", "无");
		Riddle riddle431 = new Riddle("	堤坝(打一英文单词) 	", "	 debar	", "英语", "无");
		Riddle riddle432 = new Riddle(
				"	What part of London is in Brazil? (猜一猜)	",
				"	 The letter 'L'.	", "英语", "无");
		Riddle riddle433 = new Riddle("	为立志(打一英文单词) 	",
				"	 village村民；乡村，村庄；群落。	", "英语", "无");
		Riddle riddle434 = new Riddle("	卖几颗(打一英文单词) 	", "	 magic(魔法，魔术)	",
				"英语", "无");
		Riddle riddle435 = new Riddle("	赖(打一英文单词) 	", "	 lie	", "英语", "无");
		Riddle riddle436 = new Riddle(
				"	What question can you never answer 'Yes' to? (猜一猜)	",
				"	 Are you dead?	", "英语", "无");
		Riddle riddle437 = new Riddle("	我的吻车(打一英文单词) 	",
				"	 adventure冒险活动；冒险经历；奇遇。	", "英语", "无");
		Riddle riddle438 = new Riddle("	二奶(打一英文单词) 	", "	 annoy使烦恼；令人讨厌；烦恼	",
				"英语", "无");
		Riddle riddle439 = new Riddle("	Why do people go to bed?(人们为什么睡觉？)	",
				"	 Because the bed won’t come to us．因为床不会走向我们。	", "英语", "无");
		Riddle riddle440 = new Riddle("	逮狸射死(打一英文单词) 	", "	 delicious	", "英语",
				"无");
		Riddle riddle441 = new Riddle("	摸钱袋子(打一英文单词) 	", "	 merchandise	",
				"英语", "无");
		Riddle riddle442 = new Riddle(
				"	What's the difference between a hill and a pill? (猜一猜)	",
				"	 A hill is hard to get up and a pill is hard to get down	",
				"英语", "无");
		Riddle riddle443 = new Riddle(
				"	What is deaf and dumb but always tells the truth? (打一物) 	",
				"	 mirror	", "英语", "无");
		Riddle riddle444 = new Riddle(
				"	What word can you make shorter by adding to it? (猜一猜)	",
				"	 Short.	", "英语", "无");
		Riddle riddle445 = new Riddle("	阿匹婆(打一英文名词) 	", "	 a people	", "英语",
				"无");
		Riddle riddle446 = new Riddle("	死壮(打一英文单词) 	", "	 strong强壮	", "英语", "无");
		Riddle riddle447 = new Riddle(
				"	Ten plus ten is ten.Ten minus ten is ten(打一物) 	",
				"	 手套(glove)	", "英语", "无");
		Riddle riddle448 = new Riddle(
				"	Where did Columbus stand when he discovered America? (猜一猜)	",
				"	 On his feet.	", "英语", "无");
		Riddle riddle449 = new Riddle("	得死(打一英文单词) 	", "	 dearth	", "英语", "无");
		Riddle riddle450 = new Riddle("	爷爷去世 (打一英文) 	", "	 yes.	", "英语", "无");
		Riddle riddle451 = new Riddle("	我有(打一英文单词) 	", "	 volume大量的	", "英语",
				"无");
		Riddle riddle452 = new Riddle("	柴门(打一英文单词) 	", "	 chairman	", "英语", "无");
		Riddle riddle453 = new Riddle("	楷模(打一英文单词) 	", "	 camel骆驼	", "英语", "无");
		Riddle riddle454 = new Riddle("	嬉戏(打一英文单词) 	", "	 cease	", "英语", "无");
		Riddle riddle455 = new Riddle("	想念的时候，已经是失去 (打一英文) 	", "	 miss.	",
				"英语", "无");
		Riddle riddle456 = new Riddle("	传飞客(打一英文单词) 	", "	 traffic	", "英语", "无");
		Riddle riddle457 = new Riddle("	故障女士 (打一动物) 	", "	 lady bug(瓢虫)。	",
				"英语", "无");
		Riddle riddle458 = new Riddle("	有没有你，颜色都不变 (打一英文) 	",
				"	 colour/color	", "英语", "无");
		Riddle riddle459 = new Riddle(
				"	What never asks questions but gets a lot of answers? (猜一猜)	",
				"	 dictionary	", "英语", "无");
		Riddle riddle460 = new Riddle("	俺必胜(打一英文单词) 	", "	 ambition雄心	", "英语",
				"无");
		Riddle riddle461 = new Riddle("	蔻驰(打一英文单词) 	", "	 教练；指导；受训练。	", "英语",
				"无");
		Riddle riddle462 = new Riddle(
				"	What is that which has a mouth,but never speaks, and a bed, but never sleeps in it?	(猜一猜)",
				"	 A river.	", "英语", "无");
		Riddle riddle463 = new Riddle("	额耐克(打一英语单词) 	", "	 alike(相同的，相像的 )	",
				"英语", "无");
		Riddle riddle464 = new Riddle("	我跑(打一英文单词) 	", "	 appall惊骇	", "英语", "无");
		Riddle riddle465 = new Riddle(
				"	What is never used until it's broken? (猜一猜)	", "	 An egg	",
				"英语", "无");
		Riddle riddle466 = new Riddle(
				"	What can you break with only one word? (猜一猜)	",
				"	 Silence.	", "英语", "无");
		Riddle riddle467 = new Riddle(
				"	How do we know the ocean is friendly? (猜一猜)	",
				"	 It waves.	", "英语", "无");
		Riddle riddle468 = new Riddle(
				"	What stays indoors no matter how many times you put it out? (猜一猜)	",
				"	 The light.	", "英语", "无");
		Riddle riddle469 = new Riddle("	Which horses have six legs? (猜一猜)	",
				"	 All horses have forelegs	", "英语", "无");
		Riddle riddle470 = new Riddle("	不认识(打一英文单词) 	", "	 prince王子；巨头；	",
				"英语", "无");
		Riddle riddle471 = new Riddle("	X(打一哲学用语) 	", "	 否定判断	", "英语", "无");
		Riddle riddle472 = new Riddle("	这破妮子(打一英文单词) 	", "	 Japanese	", "英语",
				"无");
		Riddle riddle473 = new Riddle("	what letter is an animal? (打一英文字母)	",
				"	 B	", "英语", "无");
		Riddle riddle474 = new Riddle("	潲哇(打一英文单词)	",
				"	 shower阵雨；淋浴；淋浴器；一大批；下阵雨；似阵雨般降落；洒落；纷纷降落。	", "英语", "无");
		Riddle riddle475 = new Riddle("	死洞(打一英文单词) 	", "	 stone(石头、宝石)	", "英语",
				"无");
		Riddle riddle476 = new Riddle(
				"	How does the sun affect weight？(太阳如何影响重量？) 	",
				"	 It makes the daylight.	", "英语", "无");
		Riddle riddle477 = new Riddle("	爱过你(打一英文单词) 	", "	 agony痛苦	", "英语", "无");
		Riddle riddle478 = new Riddle(
				"	Why can a bride hide nothing?(为什么新娘子什么也藏不住？)	",
				"	 Because someone will give her away.	", "英语", "无");
		Riddle riddle479 = new Riddle("	老爷(打一英文单词) 	", "	 lawyer律师，法学家	", "英语",
				"无");
		Riddle riddle480 = new Riddle("	冷(打一英文单词) 	", "	 learn	", "英语", "无");

		Riddle riddle481 = new Riddle("	街边行走,解闷散心(打一字) 	", "	 闺	", "字谜", "无");
		Riddle riddle482 = new Riddle("	有儿有女日子美(打一字) 	", "	 好	", "字谜", "无");
		Riddle riddle483 = new Riddle("	走了魔头,跑了小鬼(打一字) 	", "	 林	", "字谜", "无");
		Riddle riddle484 = new Riddle("	人家帘幕垂(打一字) 	", "	 扉	", "字谜", "无");
		Riddle riddle485 = new Riddle("	人到画中游(打一字) 	", "	 佃	", "字谜", "无");
		Riddle riddle486 = new Riddle("	扁舟归去(打一字) 	", "	 迥	", "字谜", "无");
		Riddle riddle487 = new Riddle("	脱贫之后心相依(打一字) 	", "	 岔	", "字谜", "无");
		Riddle riddle488 = new Riddle("	这一页(打一字) 	", "	 题(这=是)	", "字谜", "无");
		Riddle riddle489 = new Riddle("	长长的毛巾 (打一字) 	", "	 帐	", "字谜", "无");
		Riddle riddle490 = new Riddle("	失水即可(打一字) 	", "	 河	", "字谜", "无");
		Riddle riddle491 = new Riddle("	停在月亮脑袋上(打一字) 	", "	 肯	", "字谜", "无");
		Riddle riddle492 = new Riddle("	一手撑破天(打一字) 	", "	 扶	", "字谜", "无");
		Riddle riddle493 = new Riddle("	每逢佳节(打一字) 	", "	 侮	", "字谜", "无");
		Riddle riddle494 = new Riddle("	石头下有水(打一字) 	", "	 泵	", "字谜", "无");
		Riddle riddle495 = new Riddle("	楼头相会两倾心(打一字) 	", "	 枇	", "字谜", "无");
		Riddle riddle496 = new Riddle("	用心改革鼎力相助(打一字) 	", "	 荔	", "字谜", "无");
		Riddle riddle497 = new Riddle("	南边一条狗，要牵它一起走。(打一字) 	", "	 献。	", "字谜",
				"无");
		Riddle riddle498 = new Riddle("	我困在一个密室里(打一字) 	", "	 圄(“吾”寓意“我”)	",
				"字谜", "无");
		Riddle riddle499 = new Riddle("	二月(打一字) 	", "	 朋	", "字谜", "无");
		Riddle riddle500 = new Riddle("	一箭射日(打一字) 	", "	 申	", "字谜", "无");
		Riddle riddle501 = new Riddle("	生产须要配合(打一字) 	", "	 颜	", "字谜", "无");
		Riddle riddle502 = new Riddle("	一个人过七夕(打一字) 	",
				"	 死(“一”+“七”+“夕”=“死’)	", "字谜", "无");
		Riddle riddle503 = new Riddle("	干活尚需自留心(打一字谜) 	", "	 憩	", "字谜", "无");
		Riddle riddle504 = new Riddle("	湖水干涸月影无(打一字) 	", "	 古	", "字谜", "无");
		Riddle riddle505 = new Riddle("	山上有一兵，断了两条腿(打一字) 	", "	 岳	", "字谜", "无");
		Riddle riddle506 = new Riddle("	鼓声招来八方人(打一字) 	", "	 谷	", "字谜", "无");
		Riddle riddle507 = new Riddle("	一百减一  (打一字) 	", "	 白	", "字谜", "无");
		Riddle riddle508 = new Riddle("	不是门也是门 (打一字) 	", "	 扉	", "字谜", "无");
		Riddle riddle509 = new Riddle("	买前重点选择(打一字) 	", "	 实	", "字谜", "无");
		Riddle riddle510 = new Riddle("	一笔不直,二笔不曲,三笔有直有曲,数量单位却很大.(打一字) 	",
				"	 亿	", "字谜", "无");
		Riddle riddle511 = new Riddle("	下上一点(打一字) 	", "	 卞	", "字谜", "无");
		Riddle riddle512 = new Riddle("	站在狗旁边的人(打一字) 	", "	 伏	", "字谜", "无");
		Riddle riddle513 = new Riddle("	水木火土舍前二(打一字) 	", "	 灻	", "字谜", "无");
		Riddle riddle514 = new Riddle("	最合心意的字(打一字) 	", "	 恰	", "字谜", "无");
		Riddle riddle515 = new Riddle("	纵横交错(打一字) 	", "	 十	", "字谜", "无");
		Riddle riddle516 = new Riddle("	夫妻同心(打一字) 	", "	 怂	", "字谜", "无");
		Riddle riddle517 = new Riddle("	一口马打一字 	", "	 吗	", "字谜", "无");
		Riddle riddle518 = new Riddle("	路旁无足迹(打一字) 	", "	 各	", "字谜", "无");
		Riddle riddle519 = new Riddle("	没有右耳的人 (打一字) 	", "	 队(只有左耳=左耳旁)	",
				"字谜", "无");
		Riddle riddle520 = new Riddle("	无可奈何大小去(打一字) 	", "	 仁	", "字谜", "无");
		Riddle riddle521 = new Riddle("	一个人，四个叉 (打一字) 	", "	 爽	", "字谜", "无");
		Riddle riddle522 = new Riddle("	双手两边放(打一字) 	", "	 掰	", "字谜", "无");
		Riddle riddle523 = new Riddle("	受戒十八年 打一字 	", "	 械	", "字谜", "无");
		Riddle riddle524 = new Riddle("	目送倩人去(打一字) 	", "	 睛	", "字谜", "无");
		Riddle riddle525 = new Riddle("	一吼方休(打一字) 	", "	 孔	", "字谜", "无");
		Riddle riddle526 = new Riddle("	苦中十移位(打一字) 	", "	 苗	", "字谜", "无");
		Riddle riddle527 = new Riddle("	欲医相思少偏方(打一字) 	", "	 短	", "字谜", "无");
		Riddle riddle528 = new Riddle("	又小又大的是什么？(打一字) 	", "	 尖	", "字谜", "无");
		Riddle riddle529 = new Riddle("	添子固然好还是少生妙(打一字) 	", "	 女	", "字谜", "无");
		Riddle riddle530 = new Riddle("	张口结舌(打一字) 	", "	 千	", "字谜", "无");
		Riddle riddle531 = new Riddle("	一心控制人口(打一字) 	", "	 恰	", "字谜", "无");
		Riddle riddle532 = new Riddle("	下落不明心牵挂(打一字) 	", "	 芯	", "字谜", "无");
		Riddle riddle533 = new Riddle("	不走(打一字) 	", "	 还	", "字谜", "无");
		Riddle riddle534 = new Riddle("	T从口入(打一字) 	", "	 曱yuē(取物)	", "字谜", "无");
		Riddle riddle535 = new Riddle("	四木合一(打一字) 	", "	 木	", "字谜", "无");
		Riddle riddle536 = new Riddle("	秋天的鱼(打一字) 	", "	 鳅	", "字谜", "无");
		Riddle riddle537 = new Riddle("	又到双休人出游(打一字) 	", "	 桑	", "字谜", "无");
		Riddle riddle538 = new Riddle("	屋内的一方桌 (打一字) 	", "	 房	", "字谜", "无");
		Riddle riddle539 = new Riddle("	流泪出洋相(打一字) 	", "	 样	", "字谜", "无");
		Riddle riddle540 = new Riddle("	永恒的太阳(打一字) 	", "	 昶	", "字谜", "无");
		Riddle riddle541 = new Riddle("	与火共舞(打一字) 	", "	 烘	", "字谜", "无");
		Riddle riddle542 = new Riddle("	东海降雨(打一字) 	", "	 霉	", "字谜", "无");
		Riddle riddle543 = new Riddle("	岗位调动(打一字) 	", "	 岖	", "字谜", "无");
		Riddle riddle544 = new Riddle("	六十天(打一字) 	", "	 朋友(解释二个月)	", "字谜", "无");
		Riddle riddle545 = new Riddle("	地没有地(打一字) 	", "	 也	", "字谜", "无");
		Riddle riddle546 = new Riddle("	上大下小合为一，二字一去不归来(打一字) 	", "	 夵	", "字谜",
				"无");
		Riddle riddle547 = new Riddle("	树没了心(打一字) 	", "	 村	", "字谜", "无");
		Riddle riddle548 = new Riddle("	四根棍子横竖交叉(打一字) 	", "	 井	", "字谜", "无");
		Riddle riddle549 = new Riddle("	足球先生(打一字) 	", "	 呈	", "字谜", "无");
		Riddle riddle550 = new Riddle("	灾后安稳别着急(打一字) 	", "	 秋	", "字谜", "无");
		Riddle riddle551 = new Riddle("	花开除草午后培(打一字) 	", "	 华	", "字谜", "无");
		Riddle riddle552 = new Riddle("	喝水口倒干(打一字) 	", "	 洁	", "字谜", "无");
		Riddle riddle553 = new Riddle("	别人的信手勿拆(打一字) 	", "	 诉	", "字谜", "无");
		Riddle riddle554 = new Riddle("	毛遂自荐(打一字) 	", "	 衙	", "字谜", "无");
		Riddle riddle555 = new Riddle("	女尸(打一字) 	", "	 妄	", "字谜", "无");
		Riddle riddle556 = new Riddle("	两口子碰面就吵嘴(打一字) 	", "	 马	", "字谜", "无");
		Riddle riddle557 = new Riddle("	秋收之后是赛一赛(打一字) 	", "	 秕	", "字谜", "无");
		Riddle riddle558 = new Riddle("	口中有口(打一字) 	", "	 回	", "字谜", "无");
		Riddle riddle559 = new Riddle("	晴天无日不开怀(打一字) 	", "	 情	", "字谜", "无");
		Riddle riddle560 = new Riddle("	如今中国面貌改(打一字) 	", "	 玲	", "字谜", "无");

		dbRManager.insert(riddle1);
		dbRManager.insert(riddle2);
		dbRManager.insert(riddle3);
		dbRManager.insert(riddle4);
		dbRManager.insert(riddle5);
		dbRManager.insert(riddle6);
		dbRManager.insert(riddle7);
		dbRManager.insert(riddle8);
		dbRManager.insert(riddle9);
		dbRManager.insert(riddle10);
		dbRManager.insert(riddle11);
		dbRManager.insert(riddle12);
		dbRManager.insert(riddle13);
		dbRManager.insert(riddle14);
		dbRManager.insert(riddle15);
		dbRManager.insert(riddle16);
		dbRManager.insert(riddle17);
		dbRManager.insert(riddle18);
		dbRManager.insert(riddle19);
		dbRManager.insert(riddle20);
		dbRManager.insert(riddle21);
		dbRManager.insert(riddle22);
		dbRManager.insert(riddle23);
		dbRManager.insert(riddle24);
		dbRManager.insert(riddle25);
		dbRManager.insert(riddle26);
		dbRManager.insert(riddle27);
		dbRManager.insert(riddle28);
		dbRManager.insert(riddle29);
		dbRManager.insert(riddle30);
		dbRManager.insert(riddle31);
		dbRManager.insert(riddle32);
		dbRManager.insert(riddle33);
		dbRManager.insert(riddle34);
		dbRManager.insert(riddle35);
		dbRManager.insert(riddle36);
		dbRManager.insert(riddle37);
		dbRManager.insert(riddle38);
		dbRManager.insert(riddle39);
		dbRManager.insert(riddle40);
		dbRManager.insert(riddle41);
		dbRManager.insert(riddle42);
		dbRManager.insert(riddle43);
		dbRManager.insert(riddle44);
		dbRManager.insert(riddle45);
		dbRManager.insert(riddle46);
		dbRManager.insert(riddle47);
		dbRManager.insert(riddle48);
		dbRManager.insert(riddle49);
		dbRManager.insert(riddle50);
		dbRManager.insert(riddle51);
		dbRManager.insert(riddle52);
		dbRManager.insert(riddle53);
		dbRManager.insert(riddle54);
		dbRManager.insert(riddle55);
		dbRManager.insert(riddle56);
		dbRManager.insert(riddle57);
		dbRManager.insert(riddle58);
		dbRManager.insert(riddle59);
		dbRManager.insert(riddle60);
		dbRManager.insert(riddle61);
		dbRManager.insert(riddle62);
		dbRManager.insert(riddle63);
		dbRManager.insert(riddle64);
		dbRManager.insert(riddle65);
		dbRManager.insert(riddle66);
		dbRManager.insert(riddle67);
		dbRManager.insert(riddle68);
		dbRManager.insert(riddle69);
		dbRManager.insert(riddle70);
		dbRManager.insert(riddle71);
		dbRManager.insert(riddle72);
		dbRManager.insert(riddle73);
		dbRManager.insert(riddle74);
		dbRManager.insert(riddle75);
		dbRManager.insert(riddle76);
		dbRManager.insert(riddle77);
		dbRManager.insert(riddle78);
		dbRManager.insert(riddle79);
		dbRManager.insert(riddle80);
		dbRManager.insert(riddle81);
		dbRManager.insert(riddle82);
		dbRManager.insert(riddle83);
		dbRManager.insert(riddle84);
		dbRManager.insert(riddle85);
		dbRManager.insert(riddle86);
		dbRManager.insert(riddle87);
		dbRManager.insert(riddle88);
		dbRManager.insert(riddle89);
		dbRManager.insert(riddle90);
		dbRManager.insert(riddle91);
		dbRManager.insert(riddle92);
		dbRManager.insert(riddle93);
		dbRManager.insert(riddle94);
		dbRManager.insert(riddle95);
		dbRManager.insert(riddle96);
		dbRManager.insert(riddle97);
		dbRManager.insert(riddle98);
		dbRManager.insert(riddle99);
		dbRManager.insert(riddle100);
		dbRManager.insert(riddle101);
		dbRManager.insert(riddle102);
		dbRManager.insert(riddle103);
		dbRManager.insert(riddle104);
		dbRManager.insert(riddle105);
		dbRManager.insert(riddle106);
		dbRManager.insert(riddle107);
		dbRManager.insert(riddle108);
		dbRManager.insert(riddle109);
		dbRManager.insert(riddle110);
		dbRManager.insert(riddle111);
		dbRManager.insert(riddle112);
		dbRManager.insert(riddle113);
		dbRManager.insert(riddle114);
		dbRManager.insert(riddle115);
		dbRManager.insert(riddle116);
		dbRManager.insert(riddle117);
		dbRManager.insert(riddle118);
		dbRManager.insert(riddle119);
		dbRManager.insert(riddle120);
		dbRManager.insert(riddle121);
		dbRManager.insert(riddle122);
		dbRManager.insert(riddle123);
		dbRManager.insert(riddle124);
		dbRManager.insert(riddle125);
		dbRManager.insert(riddle126);
		dbRManager.insert(riddle127);
		dbRManager.insert(riddle128);
		dbRManager.insert(riddle129);
		dbRManager.insert(riddle130);
		dbRManager.insert(riddle131);
		dbRManager.insert(riddle132);
		dbRManager.insert(riddle133);
		dbRManager.insert(riddle134);
		dbRManager.insert(riddle135);
		dbRManager.insert(riddle136);
		dbRManager.insert(riddle137);
		dbRManager.insert(riddle138);
		dbRManager.insert(riddle139);
		dbRManager.insert(riddle140);
		dbRManager.insert(riddle141);
		dbRManager.insert(riddle142);
		dbRManager.insert(riddle143);
		dbRManager.insert(riddle144);
		dbRManager.insert(riddle145);
		dbRManager.insert(riddle146);
		dbRManager.insert(riddle147);
		dbRManager.insert(riddle148);
		dbRManager.insert(riddle149);
		dbRManager.insert(riddle150);
		dbRManager.insert(riddle151);
		dbRManager.insert(riddle152);
		dbRManager.insert(riddle153);
		dbRManager.insert(riddle154);
		dbRManager.insert(riddle155);
		dbRManager.insert(riddle156);
		dbRManager.insert(riddle157);
		dbRManager.insert(riddle158);
		dbRManager.insert(riddle159);
		dbRManager.insert(riddle160);
		dbRManager.insert(riddle161);
		dbRManager.insert(riddle162);
		dbRManager.insert(riddle163);
		dbRManager.insert(riddle164);
		dbRManager.insert(riddle165);
		dbRManager.insert(riddle166);
		dbRManager.insert(riddle167);
		dbRManager.insert(riddle168);
		dbRManager.insert(riddle169);
		dbRManager.insert(riddle170);
		dbRManager.insert(riddle171);
		dbRManager.insert(riddle172);
		dbRManager.insert(riddle173);
		dbRManager.insert(riddle174);
		dbRManager.insert(riddle175);
		dbRManager.insert(riddle176);
		dbRManager.insert(riddle177);
		dbRManager.insert(riddle178);
		dbRManager.insert(riddle179);
		dbRManager.insert(riddle180);
		dbRManager.insert(riddle181);
		dbRManager.insert(riddle182);
		dbRManager.insert(riddle183);
		dbRManager.insert(riddle184);
		dbRManager.insert(riddle185);
		dbRManager.insert(riddle186);
		dbRManager.insert(riddle187);
		dbRManager.insert(riddle188);
		dbRManager.insert(riddle189);
		dbRManager.insert(riddle190);
		dbRManager.insert(riddle191);
		dbRManager.insert(riddle192);
		dbRManager.insert(riddle193);
		dbRManager.insert(riddle194);
		dbRManager.insert(riddle195);
		dbRManager.insert(riddle196);
		dbRManager.insert(riddle197);
		dbRManager.insert(riddle198);
		dbRManager.insert(riddle199);
		dbRManager.insert(riddle200);
		dbRManager.insert(riddle201);
		dbRManager.insert(riddle202);
		dbRManager.insert(riddle203);
		dbRManager.insert(riddle204);
		dbRManager.insert(riddle205);
		dbRManager.insert(riddle206);
		dbRManager.insert(riddle207);
		dbRManager.insert(riddle208);
		dbRManager.insert(riddle209);
		dbRManager.insert(riddle210);
		dbRManager.insert(riddle211);
		dbRManager.insert(riddle212);
		dbRManager.insert(riddle213);
		dbRManager.insert(riddle214);
		dbRManager.insert(riddle215);
		dbRManager.insert(riddle216);
		dbRManager.insert(riddle217);
		dbRManager.insert(riddle218);
		dbRManager.insert(riddle219);
		dbRManager.insert(riddle220);
		dbRManager.insert(riddle221);
		dbRManager.insert(riddle222);
		dbRManager.insert(riddle223);
		dbRManager.insert(riddle224);
		dbRManager.insert(riddle225);
		dbRManager.insert(riddle226);
		dbRManager.insert(riddle227);
		dbRManager.insert(riddle228);
		dbRManager.insert(riddle229);
		dbRManager.insert(riddle230);
		dbRManager.insert(riddle231);
		dbRManager.insert(riddle232);
		dbRManager.insert(riddle233);
		dbRManager.insert(riddle234);
		dbRManager.insert(riddle235);
		dbRManager.insert(riddle236);
		dbRManager.insert(riddle237);
		dbRManager.insert(riddle238);
		dbRManager.insert(riddle239);
		dbRManager.insert(riddle240);
		dbRManager.insert(riddle241);
		dbRManager.insert(riddle242);
		dbRManager.insert(riddle243);
		dbRManager.insert(riddle244);
		dbRManager.insert(riddle245);
		dbRManager.insert(riddle246);
		dbRManager.insert(riddle247);
		dbRManager.insert(riddle248);
		dbRManager.insert(riddle249);
		dbRManager.insert(riddle250);
		dbRManager.insert(riddle251);
		dbRManager.insert(riddle252);
		dbRManager.insert(riddle253);
		dbRManager.insert(riddle254);
		dbRManager.insert(riddle255);
		dbRManager.insert(riddle256);
		dbRManager.insert(riddle257);
		dbRManager.insert(riddle258);
		dbRManager.insert(riddle259);
		dbRManager.insert(riddle260);
		dbRManager.insert(riddle261);
		dbRManager.insert(riddle262);
		dbRManager.insert(riddle263);
		dbRManager.insert(riddle264);
		dbRManager.insert(riddle265);
		dbRManager.insert(riddle266);
		dbRManager.insert(riddle267);
		dbRManager.insert(riddle268);
		dbRManager.insert(riddle269);
		dbRManager.insert(riddle270);
		dbRManager.insert(riddle271);
		dbRManager.insert(riddle272);
		dbRManager.insert(riddle273);
		dbRManager.insert(riddle274);
		dbRManager.insert(riddle275);
		dbRManager.insert(riddle276);
		dbRManager.insert(riddle277);
		dbRManager.insert(riddle278);
		dbRManager.insert(riddle279);
		dbRManager.insert(riddle280);
		dbRManager.insert(riddle281);
		dbRManager.insert(riddle282);
		dbRManager.insert(riddle283);
		dbRManager.insert(riddle284);
		dbRManager.insert(riddle285);
		dbRManager.insert(riddle286);
		dbRManager.insert(riddle287);
		dbRManager.insert(riddle288);
		dbRManager.insert(riddle289);
		dbRManager.insert(riddle290);
		dbRManager.insert(riddle291);
		dbRManager.insert(riddle292);
		dbRManager.insert(riddle293);
		dbRManager.insert(riddle294);
		dbRManager.insert(riddle295);
		dbRManager.insert(riddle296);
		dbRManager.insert(riddle297);
		dbRManager.insert(riddle298);
		dbRManager.insert(riddle299);
		dbRManager.insert(riddle300);
		dbRManager.insert(riddle301);
		dbRManager.insert(riddle302);
		dbRManager.insert(riddle303);
		dbRManager.insert(riddle304);
		dbRManager.insert(riddle305);
		dbRManager.insert(riddle306);
		dbRManager.insert(riddle307);
		dbRManager.insert(riddle308);
		dbRManager.insert(riddle309);
		dbRManager.insert(riddle310);
		dbRManager.insert(riddle311);
		dbRManager.insert(riddle312);
		dbRManager.insert(riddle313);
		dbRManager.insert(riddle314);
		dbRManager.insert(riddle315);
		dbRManager.insert(riddle316);
		dbRManager.insert(riddle317);
		dbRManager.insert(riddle318);
		dbRManager.insert(riddle319);
		dbRManager.insert(riddle320);
		dbRManager.insert(riddle321);
		dbRManager.insert(riddle322);
		dbRManager.insert(riddle323);
		dbRManager.insert(riddle324);
		dbRManager.insert(riddle325);
		dbRManager.insert(riddle326);
		dbRManager.insert(riddle327);
		dbRManager.insert(riddle328);
		dbRManager.insert(riddle329);
		dbRManager.insert(riddle330);
		dbRManager.insert(riddle331);
		dbRManager.insert(riddle332);
		dbRManager.insert(riddle333);
		dbRManager.insert(riddle334);
		dbRManager.insert(riddle335);
		dbRManager.insert(riddle336);
		dbRManager.insert(riddle337);
		dbRManager.insert(riddle338);
		dbRManager.insert(riddle339);
		dbRManager.insert(riddle340);
		dbRManager.insert(riddle341);
		dbRManager.insert(riddle342);
		dbRManager.insert(riddle343);
		dbRManager.insert(riddle344);
		dbRManager.insert(riddle345);
		dbRManager.insert(riddle346);
		dbRManager.insert(riddle347);
		dbRManager.insert(riddle348);
		dbRManager.insert(riddle349);
		dbRManager.insert(riddle350);
		dbRManager.insert(riddle351);
		dbRManager.insert(riddle352);
		dbRManager.insert(riddle353);
		dbRManager.insert(riddle354);
		dbRManager.insert(riddle355);
		dbRManager.insert(riddle356);
		dbRManager.insert(riddle357);
		dbRManager.insert(riddle358);
		dbRManager.insert(riddle359);
		dbRManager.insert(riddle360);
		dbRManager.insert(riddle361);
		dbRManager.insert(riddle362);
		dbRManager.insert(riddle363);
		dbRManager.insert(riddle364);
		dbRManager.insert(riddle365);
		dbRManager.insert(riddle366);
		dbRManager.insert(riddle367);
		dbRManager.insert(riddle368);
		dbRManager.insert(riddle369);
		dbRManager.insert(riddle370);
		dbRManager.insert(riddle371);
		dbRManager.insert(riddle372);
		dbRManager.insert(riddle373);
		dbRManager.insert(riddle374);
		dbRManager.insert(riddle375);
		dbRManager.insert(riddle376);
		dbRManager.insert(riddle377);
		dbRManager.insert(riddle378);
		dbRManager.insert(riddle379);
		dbRManager.insert(riddle380);
		dbRManager.insert(riddle381);
		dbRManager.insert(riddle382);
		dbRManager.insert(riddle383);
		dbRManager.insert(riddle384);
		dbRManager.insert(riddle385);
		dbRManager.insert(riddle386);
		dbRManager.insert(riddle387);
		dbRManager.insert(riddle388);
		dbRManager.insert(riddle389);
		dbRManager.insert(riddle390);
		dbRManager.insert(riddle391);
		dbRManager.insert(riddle392);
		dbRManager.insert(riddle393);
		dbRManager.insert(riddle394);
		dbRManager.insert(riddle395);
		dbRManager.insert(riddle396);
		dbRManager.insert(riddle397);
		dbRManager.insert(riddle398);
		dbRManager.insert(riddle399);
		dbRManager.insert(riddle400);
		dbRManager.insert(riddle401);
		dbRManager.insert(riddle402);
		dbRManager.insert(riddle403);
		dbRManager.insert(riddle404);
		dbRManager.insert(riddle405);
		dbRManager.insert(riddle406);
		dbRManager.insert(riddle407);
		dbRManager.insert(riddle408);
		dbRManager.insert(riddle409);
		dbRManager.insert(riddle410);
		dbRManager.insert(riddle411);
		dbRManager.insert(riddle412);
		dbRManager.insert(riddle413);
		dbRManager.insert(riddle414);
		dbRManager.insert(riddle415);
		dbRManager.insert(riddle416);
		dbRManager.insert(riddle417);
		dbRManager.insert(riddle418);
		dbRManager.insert(riddle419);
		dbRManager.insert(riddle420);
		dbRManager.insert(riddle421);
		dbRManager.insert(riddle422);
		dbRManager.insert(riddle423);
		dbRManager.insert(riddle424);
		dbRManager.insert(riddle425);
		dbRManager.insert(riddle426);
		dbRManager.insert(riddle427);
		dbRManager.insert(riddle428);
		dbRManager.insert(riddle429);
		dbRManager.insert(riddle430);
		dbRManager.insert(riddle431);
		dbRManager.insert(riddle432);
		dbRManager.insert(riddle433);
		dbRManager.insert(riddle434);
		dbRManager.insert(riddle435);
		dbRManager.insert(riddle436);
		dbRManager.insert(riddle437);
		dbRManager.insert(riddle438);
		dbRManager.insert(riddle439);
		dbRManager.insert(riddle440);
		dbRManager.insert(riddle441);
		dbRManager.insert(riddle442);
		dbRManager.insert(riddle443);
		dbRManager.insert(riddle444);
		dbRManager.insert(riddle445);
		dbRManager.insert(riddle446);
		dbRManager.insert(riddle447);
		dbRManager.insert(riddle448);
		dbRManager.insert(riddle449);
		dbRManager.insert(riddle450);
		dbRManager.insert(riddle451);
		dbRManager.insert(riddle452);
		dbRManager.insert(riddle453);
		dbRManager.insert(riddle454);
		dbRManager.insert(riddle455);
		dbRManager.insert(riddle456);
		dbRManager.insert(riddle457);
		dbRManager.insert(riddle458);
		dbRManager.insert(riddle459);
		dbRManager.insert(riddle460);
		dbRManager.insert(riddle461);
		dbRManager.insert(riddle462);
		dbRManager.insert(riddle463);
		dbRManager.insert(riddle464);
		dbRManager.insert(riddle465);
		dbRManager.insert(riddle466);
		dbRManager.insert(riddle467);
		dbRManager.insert(riddle468);
		dbRManager.insert(riddle469);
		dbRManager.insert(riddle470);
		dbRManager.insert(riddle471);
		dbRManager.insert(riddle472);
		dbRManager.insert(riddle473);
		dbRManager.insert(riddle474);
		dbRManager.insert(riddle475);
		dbRManager.insert(riddle476);
		dbRManager.insert(riddle477);
		dbRManager.insert(riddle478);
		dbRManager.insert(riddle479);
		dbRManager.insert(riddle480);
		dbRManager.insert(riddle481);
		dbRManager.insert(riddle482);
		dbRManager.insert(riddle483);
		dbRManager.insert(riddle484);
		dbRManager.insert(riddle485);
		dbRManager.insert(riddle486);
		dbRManager.insert(riddle487);
		dbRManager.insert(riddle488);
		dbRManager.insert(riddle489);
		dbRManager.insert(riddle490);
		dbRManager.insert(riddle491);
		dbRManager.insert(riddle492);
		dbRManager.insert(riddle493);
		dbRManager.insert(riddle494);
		dbRManager.insert(riddle495);
		dbRManager.insert(riddle496);
		dbRManager.insert(riddle497);
		dbRManager.insert(riddle498);
		dbRManager.insert(riddle499);
		dbRManager.insert(riddle500);
		dbRManager.insert(riddle501);
		dbRManager.insert(riddle502);
		dbRManager.insert(riddle503);
		dbRManager.insert(riddle504);
		dbRManager.insert(riddle505);
		dbRManager.insert(riddle506);
		dbRManager.insert(riddle507);
		dbRManager.insert(riddle508);
		dbRManager.insert(riddle509);
		dbRManager.insert(riddle510);
		dbRManager.insert(riddle511);
		dbRManager.insert(riddle512);
		dbRManager.insert(riddle513);
		dbRManager.insert(riddle514);
		dbRManager.insert(riddle515);
		dbRManager.insert(riddle516);
		dbRManager.insert(riddle517);
		dbRManager.insert(riddle518);
		dbRManager.insert(riddle519);
		dbRManager.insert(riddle520);
		dbRManager.insert(riddle521);
		dbRManager.insert(riddle522);
		dbRManager.insert(riddle523);
		dbRManager.insert(riddle524);
		dbRManager.insert(riddle525);
		dbRManager.insert(riddle526);
		dbRManager.insert(riddle527);
		dbRManager.insert(riddle528);
		dbRManager.insert(riddle529);
		dbRManager.insert(riddle530);
		dbRManager.insert(riddle531);
		dbRManager.insert(riddle532);
		dbRManager.insert(riddle533);
		dbRManager.insert(riddle534);
		dbRManager.insert(riddle535);
		dbRManager.insert(riddle536);
		dbRManager.insert(riddle537);
		dbRManager.insert(riddle538);
		dbRManager.insert(riddle539);
		dbRManager.insert(riddle540);
		dbRManager.insert(riddle541);
		dbRManager.insert(riddle542);
		dbRManager.insert(riddle543);
		dbRManager.insert(riddle544);
		dbRManager.insert(riddle545);
		dbRManager.insert(riddle546);
		dbRManager.insert(riddle547);
		dbRManager.insert(riddle548);
		dbRManager.insert(riddle549);
		dbRManager.insert(riddle550);
		dbRManager.insert(riddle551);
		dbRManager.insert(riddle552);
		dbRManager.insert(riddle553);
		dbRManager.insert(riddle554);
		dbRManager.insert(riddle555);
		dbRManager.insert(riddle556);
		dbRManager.insert(riddle557);
		dbRManager.insert(riddle558);
		dbRManager.insert(riddle559);
		dbRManager.insert(riddle560);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
