/*
 * CreateHashTable.java
 * <pre>
 * ハッシュ表を作るためのプログラム.
 * ハッシュ化するメソッドを持つ
 * </pre>
 * @author Ryosuke Sasaki
 */
package ryosuke;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class CreateHashTable{
    public static void main (String[] args) {
	initialize(); // hashフォルダの初期化
	try{
	    for(int fileNum = 0; fileNum < Def.READFILENAMES.length; fileNum++){
		// ファイル読み込みの準備
		String path = Def.HOMEPATH + Def.READFILENAMES[fileNum] + Def.EXTENSION;
		FileInputStream fileRead = new FileInputStream(path);
		InputStreamReader filereader = new InputStreamReader(fileRead,"UTF-8"); // UTF-8
		BufferedReader br = new BufferedReader(filereader);
 
		String row;
		int lineNum = 1;
 
		// ファイルを一行ごとに読み込む
		while ((row = br.readLine()) != null){
		    String[] strSplit = row.split("\t");
		    String appenedStr = "";
		    // screenNameとcontentに分けたものをまとめて一つの文字列を作成
		    for(int i = 0; i < strSplit.length; i++){
			appenedStr += strSplit[i];
		    }
		    String encryptCode = encryptStr(appenedStr); // 64桁の暗号へハッシュ化
 
		    // ファイル書き込みの準備
		    String fileName = Def.HASHPATH + encryptCode.substring(0,2) + Def.EXTENSION;
		    File file = new File(fileName);
		    FileWriter filewriter = new FileWriter(file, true);
 
		    //ファイル書き込み (ハッシュ化した文字列,ファイル名,行番号を一列として書き込む)
		    filewriter.write(encryptCode + "\t" + Def.READFILENAMES[fileNum].split("-")[0]
				     + "/" + Integer.toString(lineNum) + "\n");
 
		    filewriter.close();
		    lineNum++; // 行番号をインクリメントする
		}
		// ファイルを閉じる
		br.close();
	    }
 
	}catch (IOException e) {
	    e.printStackTrace();
	}
    }
 
    /*
     * hashフォルダを初期化する
     * @author Ryosuke Sasaki
     */
    private static void initialize(){
	File file = new File(Def.HASHPATH);
	if (file.exists()){
	    System.out.println("hashフォルダが存在します.一度消去して新たなhashフォルダを作成します.");
	    delete(file); // フォルダごと削除
	}else{
	    System.out.println("hashフォルダが存在しません.これからhashフォルダを作成します.");
	}
	file.mkdir(); // 新たにフォルダを作成
    }
 
    /*
     * ディレクトリにファイルが含まれていても全てを消去する
     * @author http://www.my-notebook.net/10d38888-00d9-4fd0-8fe9-1fc60dfa4f5d.html
     * @param file消去するファイルもしくはフォルダ
     */
    static private void delete(File file){
	if(file.exists() == false){
	    return ;
	}
 
	if(file.isFile()){
	    file.delete();
	}
 
	if(file.isDirectory()){
	    File[] files = file.listFiles();
	    for(int i = 0; i < files.length; i++){
		delete(files[i]);
	    }
	    file.delete();
	}
    }
 
    /*
     * 文字列を64桁のハッシュ値にする
     * @author http://itmemo.net-luck.com/java-messagedigest-hash/
     * @param textハッシュ化する文字列
     * @return ハッシュ化された文字列
     */
    public static String encryptStr(String text) {
	// 変数初期化
	MessageDigest md = null;
	StringBuffer buffer = new StringBuffer();
 
	try {
	    // メッセージダイジェストインスタンス取得
	    md = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException e) {
	    // 例外発生時、エラーメッセージ出力
	    System.out.println("指定された暗号化アルゴリズムがありません");
	}
 
	// メッセージダイジェスト更新
	md.update(text.getBytes());
 
	// ハッシュ値を格納
	byte[] valueArray = md.digest();
 
	// ハッシュ値の配列をループ
	for(int i = 0; i < valueArray.length; i++){
	    String tmpStr = Integer.toHexString(valueArray[i] & 0xff);// 値の符号を反転させ、16進数に変換
	    if(tmpStr.length() == 1){ // 値が一桁だった場合、先頭に0を追加し、バッファに追加
		buffer.append('0').append(tmpStr);
	    } else {// その他の場合、バッファに追加
		buffer.append(tmpStr);
	    }
	}
	return buffer.toString(); // 完了したハッシュ計算値を返却
    }
 
}