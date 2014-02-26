/*
 * Def.java
 * <pre>
 * 変更可能な部分をまとめた設定ファイル.
 * 絶対に値が変わらない定数のみをまとめている.
 * </pre>
 * @author Ryosuke Sasaki
 */
package ryosuke;
 
public class Def {
    // 作業フォルダ用のパス
    public static final String HOMEPATH = "C:\\Users\\ryosuke\\Desktop\\RTFile\\";
    // ハッシュファイルを作るためのパス
    public static final String HASHPATH = HOMEPATH + "hash\\";
    // 拡張子
    public static final String EXTENSION = ".dat";
    // 加工済みのファイル(cut.shを実行するとこのファイルが生成される)
    public static final String[] READFILENAMES = {"2011_03_22-cut"};
    //public static final String[] READFILENAMES = {"2011_03_22-cut", "2011_03_23-cut"};
    //public static final String[] READFILENAMES = {"test-cut"};
 
}