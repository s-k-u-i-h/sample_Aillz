package sample_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BJ{
	
	public static void main(String[] args) {
		
		System.out.println("ゲームを開始します");
		
		List<Integer>deck = new ArrayList<>(52);
		shuffleDeck(deck);
		
		
		//手札のリスト作成
		List<Integer>player = new ArrayList<>();
		List<Integer>dealer = new ArrayList<>();
		
		//両者カードを2枚引く
		player.add(deck.get(0));
		dealer.add(deck.get(1));
		player.add(deck.get(2));
		dealer.add(deck.get(3));
		
		//山札の状況を記録する変数定義
		int deckCount = 4;
		
		//手札枚数を記録する変数を定義
		int playerHands = 2;
		
		//両者のポイントを表示
		System.out.println("Playerの1枚目は"+toDescription (player.get(0)));
		System.out.println("Dealerの1枚目は"+toDescription (dealer.get(0)));
		System.out.println("Playerの2枚目は"+toDescription (player.get(1)));
		
		int playerPoint = totalPoint(player);
		int dealerPoint = totalPoint(dealer);
		
		//playerのポイント集計
		System.out.println("Playerは現在"+playerPoint+"点");

		//プレイヤーフェーズ
		while(true) {
			System.out.println("カードを引きますか？（YES:y or NO:n）");
			Scanner scanner =new Scanner(System.in);
			String str =scanner.next();
			
			if("n".equals(str)){
				break;				
			}else if("y".equals(str)){
				player.add(deck.get(deckCount));
				deckCount ++ ;
				playerHands ++ ;
				System.out.println("playerの"+playerHands+"枚目のカードは"+toDescription (player.get(playerHands-1)));
				playerPoint = totalPoint(player);
				System.out.println("playerの合計は"+playerPoint);
				//バーストの判定
				if(isBusted(playerPoint)) {
					System.out.println("Bustです。");
					return;
				}
			}else{
				System.out.println("あなたの入力は" + str + " です。y か n を入力してください。");
			}
			
		}
		
		//ディーラーフェーズ（17以上になるまで追加）
		while(true) {
			if(dealerPoint>=17) {
				break;
			}else{
				dealer.add(deck.get(deckCount));
				deckCount ++;
				dealerPoint = totalPoint(dealer);
				System.out.println("dealerの合計は"+dealerPoint);
				if(isBusted(dealerPoint)) {
					System.out.println("dealerがBustしました。あなたの勝ちです。");
					return;
				}
			}
		}
		
		//ポイント比較
		System.out.println("Playerの得点："+totalPoint(player)+"点");
		System.out.println("Dealerの得点："+totalPoint(dealer)+"点");
		
		//判定
		if(playerPoint == dealerPoint) {
			System.out.println("引き分け");
		}else if(playerPoint > dealerPoint) {
			System.out.println("Playerの勝利");
		}else {
			System.out.println("敗北・・・");
		}	
	}

	//山札初期値→シャッフル
	private static void shuffleDeck(List<Integer>deck) {
		//リストに1-52代入
		for(int i=1;i<=52;i++) {
			deck.add(i);
		}

		//山札シャッフル
		Collections.shuffle(deck);

		//リストの中身を確認
		/*for(int i=0;i<=deck.size();i++) {
	System.out.println(deck.get(i));
	}*/
	}

	//Bustの判定
	private static boolean isBusted(int point) {
		if(21>=point) {
			return false;
		}else {
			return true;
		}
	}

	//現在ポイントの合計
	private static int totalPoint(List<Integer>list){
		int total = 0;
		for(int i=0;i<list.size();i++){
			total = total + toPoint(toNumber(list.get(i)));
		}
		return total;
	}

	//山札の数を絵柄に置き換える
	private static String toSuit (int cardNumber) {
		switch((cardNumber-1)/13){
			case 0:
				return "クラブ";
			case 1:
				return "ダイヤ";
			case 2:
				return "ハート";
			case 3:
				return "スペード";
			default:
				return "例外";
		}
	}

	private static int toNumber(int cardNumber) {
		int number = cardNumber%13;
		if(number == 0) {
			number = 13;
		}
		return number;
	}

	//引いたカード番号を文字列（AJQK）に変換
	private static String toRank (int number) {
		switch(number){
			case 1:
				return "A";
			case 11:
				return "J";
			case 12:
				return "Q";
			case 13:
				return "K";
			default:
				String str = String.valueOf(number);
				return str;
		}
	}

	//山札の番号を文字列（AJQK）に変換
	private static String toDescription (int cardNumber) {
		String rank = toRank(toNumber(cardNumber));
		String suit = toSuit(cardNumber);
		return suit+ "の" +rank;
	}

	//絵札をポイントに変換
	private static int toPoint(int num) {
		if(num==11||num==12||num==13) {
			num = 10;
		}
		return num;
	}

}