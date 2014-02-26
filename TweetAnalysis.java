/*
 * TweetAnalysis.java
 * <pre>
 * リツイートのリンク解析をするための中心となるプログラム
 * </pre>
 * @author Ryosuke Sasaki
 */
package ryosuke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
 
class TweetAnalysis {
    public static void main(String args[]){
 
	// ハッシュファイルが存在するかどうかの確認
	if(hashFileExist() == false){
	    System.exit(0);
	}
	try{
	    // ファイルの数だけ繰り返す
	    for(int fileNum = 0; fileNum < Def.READFILENAMES.length; fileNum++){
 
		// 読み込み用ファイルの設定
		String path = Def.HOMEPATH + Def.READFILENAMES[fileNum] + Def.EXTENSION;
		FileInputStream file = new FileInputStream(path);
		InputStreamReader filereader = new InputStreamReader(file,"UTF-8");
		BufferedReader br = new BufferedReader(filereader);
 
		String row; // 読み込んだ行を保存する変数
		int lineCount = 1; // 現在読み込んでいる行番号を保存する変数
 
		// ファイルを1行ずつ読み込む
		while((row = br.readLine()) != null){
		    TweetClass tc = new TweetClass();
		    // 最初のscreenNameだけは切り出す
		    tc.firstDivide(row);
		    // RTをしているところで分割してRTをリスト状につなげる
		    tc.divide(tc.content);
		    // screenNameとcontentをハッシュ化して鍵を保存する
		    tc.createHashCode();
		    // 鍵をもとにハッシュファイルから検索して元データの位置を割り出す
		    tc.hashSearch();
		    // RTの関係をファイルに書き出す
		    tc.displayRTRelation(0); // 0はタブ数
		    // デバッグ用関数(tcに何が保存されているかを書き出す)
		    tc.debugLog(Def.READFILENAMES[fileNum], lineCount++);
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    /*
     * ハッシュファイルが存在するかどうかを確認するメソッド
     * @author Ryosuke Sasaki
     */
    private static boolean hashFileExist(){
	File file = new File(Def.HASHPATH);
	if (file.exists()){
	    System.out.println("hashファイルが存在します.");
	    return true;
	}else{
	    System.out.println("hashファイルが存在しないので検索が出来ません. 強制終了します.");
	    return false;
	}
    }
 
}