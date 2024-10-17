import model.Memo
import util.FileMemoDatabase
import util.IMemoDatabase

fun main(args: Array<String>) {
   println("MAIN ENTRY POINT")

   val memoDatabase: IMemoDatabase = FileMemoDatabase.getInstance()

   val res = memoDatabase.writeMemo(
      listOf(
         Memo(
            id = 1, content = "A"
         ),
         Memo(
            id = 2, content = "B"
         ),
         Memo(
            id = 3, content = "C"
         ),
      )
   )

//   val memos = memoDatabase.readMemo()

//   println(memos)
}