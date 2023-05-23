package trump.fukushima;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import trump.Trump3;

public class TrumpGameBj {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ

		int money = 50000;
		int con = 0;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Trump3[] card = new Trump3[52];
		Random rnd = new Random();
		int ran;
		ran = rnd.nextInt(52) + 1;
		card[0] = new Trump3(ran, "");
		ArrayList<Trump3> trumpList = new ArrayList<Trump3>();

		//ランダムで数字格納
		for (int i = 1; i < 52; i++) {
			ran = rnd.nextInt(52) + 1;
			card[i] = new Trump3(ran, "");

			for (int k = 0; k < i; k++) {
				if (card[k].getNum() == card[i].getNum()) {
					ran = rnd.nextInt(52) + 1;
					card[i] = new Trump3(ran, "");
					k = -1;
				}
			}
		}

		//マーク判定
		for (int l = 0; l < 52; l++) {
			if (card[l].getNum() < 14) {
				card[l].setMark("ハート");
				trumpList.add(card[l]);
			} else if (card[l].getNum() < 27) {
				card[l].setMark("クローバー");
				card[l] = new Trump3(card[l].getNum() - 13, card[l].getMark());
				trumpList.add(card[l]);
			} else if (card[l].getNum() < 40) {
				card[l].setMark("ダイヤ");
				card[l] = new Trump3(card[l].getNum() - 26, card[l].getMark());
				trumpList.add(card[l]);
			} else if (card[l].getNum() < 53) {
				card[l].setMark("スペード");
				card[l] = new Trump3(card[l].getNum() - 39, card[l].getMark());
				trumpList.add(card[l]);
			}
		}
		int aVal = 0;

		while (money != 0 && con == 0) {

			if (aVal + 4 > 51) {
				Collections.shuffle(trumpList);
				aVal = 0;
			}

			//スタート
			System.out.println("--ブラックジャック--");
			int d = 0;
			int c = 0;
			int judge = 0;
			int use1 = 0;
			int use2 = 0;

			System.out.println("所持金:" + money);
			System.out.print("何円かけますか");
			String strM = br.readLine();
			int valM = Integer.parseInt(strM);
			
			if(valM <= 0) {
				continue;
			}

			if (money - valM < 0) {
				System.out.println("所持金:" + money);
				continue;
			}
			
			ArrayList<Trump3> arrayList = new ArrayList<Trump3>();
			ArrayList<Trump3> arrayList1 = new ArrayList<Trump3>();
			arrayList.add(trumpList.get(aVal + 2));
			arrayList.add(trumpList.get(aVal + 3));
			arrayList1.add(trumpList.get(aVal));
			arrayList1.add(trumpList.get(aVal + 1));
			System.out.println("ディーラー:" + trumpList.get(aVal).getMark() + trumpList.get(aVal).getNum());
			System.out.println("自分:" + trumpList.get(aVal + 2).getMark() + trumpList.get(aVal + 2).getNum() + ","
					+ trumpList.get(aVal + 3).getMark() + trumpList.get(aVal + 3).getNum());

			d = sumNum(trumpList.get(aVal).getNum(), trumpList.get(aVal + 1).getNum());
			c = sumNum(trumpList.get(aVal + 2).getNum(), trumpList.get(aVal + 3).getNum());

			if (trumpList.get(aVal).getNum() == 1 || trumpList.get(aVal + 1).getNum() == 1) {
				use2++;
			}
			if (trumpList.get(aVal + 2).getNum() == 1 || trumpList.get(aVal + 3).getNum() == 1) {
				use1++;
			}
			sumOut(c, trumpList.get(aVal + 2).getNum(), trumpList.get(aVal + 3).getNum());

			if (bm(c, trumpList.get(aVal + 2).getNum(), trumpList.get(aVal + 3).getNum(), valM) != 0) {
				money = money - valM;
				money = money + bm(c, trumpList.get(aVal + 2).getNum(), trumpList.get(aVal + 3).getNum(), valM);
				valM = 0;
			}

			aVal = aVal + 3;

			//引く
			while (judge != 1 && c != 21) {
				System.out.print("0:引く 1:終わりを入力してください。");
				String strInput = br.readLine();
				int val = Integer.parseInt(strInput);

				if (val == 0) {

					c = sumNum2(c, trumpList.get(aVal + 1).getNum());
					arrayList.add(trumpList.get(aVal + 1));
					if (trumpList.get(aVal + 1).getNum() == 1) {
						use1++;
					}
					System.out
							.println("引いたカード:" + trumpList.get(aVal + 1).getMark() + trumpList.get(aVal + 1).getNum());
					System.out.println("持ってるカード");
					for (Trump3 trump3 : arrayList) {
						System.out.print(trump3.getMark() + trump3.getNum() + "\t");

					}

					aVal = aVal + 1;

					if (aVal == 51) {
						Collections.shuffle(trumpList);
						aVal = 0;
					}

					for (int i = 0; i < use1; i++) {
						if (c > 21) {
							c = c - 10;
							use1--;
						}
					}

					System.out.println("合計:" + c);

					if (c > 21) {
						System.out.println("バースト");
						money = money - valM;
						judge++;
					}
				} else {
					judge++;
				}
			}
			System.out.println();

			//引くディーラー
			System.out.println("ディーラー");
			for (Trump3 trump3 : arrayList1) {
				System.out.print(trump3.getMark() + trump3.getNum() + "\t");
			}
			System.out.println();

			while (d < 17) {
				d = sumNum2(d, trumpList.get(aVal + 1).getNum());
				arrayList1.add(trumpList.get(aVal + 1));
				if (trumpList.get(aVal + 1).getNum() == 1) {
					use2++;
				}
				System.out.println("引いたカード:" + trumpList.get(aVal + 1).getMark() + trumpList.get(aVal + 1).getNum());

				for (Trump3 trump3 : arrayList1) {
					System.out.print(trump3.getMark() + trump3.getNum() + "\t");
				}
				System.out.println();

				for (int i = 0; i < use2; i++) {
					if (d > 21) {
						d = d - 10;
						use2--;
					}
				}
				aVal = aVal + 1;
				if (aVal == 51) {
					Collections.shuffle(trumpList);
					aVal = 0;
				}
			}

			//判定
			System.out.println();
			System.out.println("ディーラー:" + d);
			System.out.println("自分:" + c);
			System.out.println("持ってるカード");
			for (Trump3 trump3 : arrayList) {
				System.out.print(trump3.getMark() + trump3.getNum() + "\t");
			}
			System.out.println();

			if (arrayList.size() == 2 && c == 21) {
				System.out.println("勝ちです");

			} else {
				result(c, d);
			}

			if (c < 22 && d < 22) {
				if (c > d) {
					money = money - valM;
					valM = valM * 15;
					money = money + (valM / 10);
				} else if (c < d) {
					money = money - valM;
				}
			} else if (c < 22 && d > 21) {
				money = money - valM;
				valM = valM * 15;
				money = money + (valM / 10);
			}

			System.out.println("所持金:" + money);
			if(money <= 0) {
				System.out.println("0円になったので終了です。");
				con = 1;
				break;
			}
			System.out.print("0:続ける 1:終わりを入力してください。");
			String strCon = br.readLine();
			con = Integer.parseInt(strCon);
		}
	}

	public static int sumNum(int num, int num2) {

		if ((num == 1 || num2 == 1) && (num > 9 || num2 > 9)) {
			num = 21;
		} else if (num > 10 && num2 > 10) {
			num = 20;
		} else if (num > 10) {
			num = num2 + 10;
		} else if (num2 > 10) {
			num = num + 10;
		} else {

			if (num == 1 && num2 == 1) {
				num = 12;
			} else if (num == 1) {
				num = 11 + num2;
			} else if (num2 == 1) {
				num = 11 + num;
			} else {
				num = num + num2;
			}
		}
		return num;
	}

	public static int sumNum2(int num, int num2) {

		if (num2 == 1) {
			num = num + 11;
		} else if (num2 > 10) {
			num = num + 10;
		} else {
			num = num + num2;
		}

		return num;

	}

	//メソッド
	public static void sumOut(int num, int num2, int num3) {
		if (num == 21 && (num2 > 10 || num3 > 10)) {
			System.out.println("21 ブラックジャックです");
		} else {
			System.out.println("合計:" + num);
		}
	}

	public static int bm(int num, int num2, int num3, int num4) {
		if (num == 21 && (num2 > 10 || num3 > 10)) {
			return num4 * 2;
		} else {
			return 0;
		}
	}

	//メソッド
	public static void result(int num, int num2) {
		if (num > 21) {
			System.out.println("負けです。");
		} else if (num2 > 21) {
			System.out.println("勝ちです。");
		} else if (num > num2) {
			System.out.println("勝ちです。");
		} else {
			System.out.println("負けです。");
		}
	}
}
