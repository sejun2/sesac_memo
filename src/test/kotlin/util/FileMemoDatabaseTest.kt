package util

import io.mockk.every
import io.mockk.spyk
import model.Category
import model.Memo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class FileMemoDatabaseTest {

    private lateinit var fileMemoDatabase: FileMemoDatabase
    private lateinit var fileMemoDatabaseForFail: FileMemoDatabase
    private lateinit var memos: List<Memo>
    private lateinit var file: File
    private lateinit var mockFile: File
    private lateinit var memo: Memo

    private val MEMO_FILE_NAME = "memo_test_file.txt"

    @BeforeTest
    fun beforeTest() {
        memos = listOf(
            Memo(
                id = 1, content = "A", Category.DAILY
            ),
            Memo(
                id = 2, content = "B", Category.ECONOMY
            ),
            Memo(
                id = 3, content = "C", Category.TECH
            ),
        )
        mockFile = spyk(File("fail_memo_test_file.txt"))
        every { mockFile.exists() } returns false
        file = File(MEMO_FILE_NAME)
        fileMemoDatabase = FileMemoDatabase.getInstance(file)
        fileMemoDatabaseForFail = FileMemoDatabase.getInstance(mockFile)
        memo = Memo(4, "D", Category.TECH)
    }

    @AfterTest
    fun afterTest() {
        // remove test memo file after test
        file.delete()
        mockFile.delete()
        FileMemoDatabase.disposeInstance(file.name)
        FileMemoDatabase.disposeInstance(mockFile.name)
    }

    @Test
    fun fileMemoDatabase_singleton_test() {
        val instance1 = FileMemoDatabase.getInstance()
        val instance2 = FileMemoDatabase.getInstance()

        assertEquals(instance1.hashCode(), instance2.hashCode())
    }

    @Test
    fun `when write memo success, then return true`() {
        val res = fileMemoDatabase.writeMemo(memos)
        assertEquals(true, res)
    }

    @Test
    fun `when write memo failed, then return false`() {
        val res = fileMemoDatabaseForFail.writeMemo(memos)

        assertEquals(false, res)
    }

    @Test
    fun readMemo() {
        fileMemoDatabase = FileMemoDatabase.getInstance()
        fileMemoDatabase.writeMemo(memos)
        val res = fileMemoDatabase.readMemo()

        assertArrayEquals(memos.toTypedArray(), res.toTypedArray())
    }

    @Test
    fun addMemo() {
        fileMemoDatabase = FileMemoDatabase.getInstance()
        val initialSize = fileMemoDatabase.readMemo().size

        fileMemoDatabase.addMemo(memo)
        val res = fileMemoDatabase.readMemo()

        assertAll(
            { assertEquals(initialSize + 1, res.size) },  // 크기가 1 증가했는지 확인
            { assertTrue(res.contains(memo)) },           // 추가한 메모가 존재하는지 확인
            { assertEquals(memo, res[res.size - 1]) }  // 마지막에 추가되었는지 확인
        )

    }

    @Test
    fun deleteMemo() {
        fileMemoDatabase = FileMemoDatabase.getInstance()
        val initialSize = fileMemoDatabase.readMemo().size

        val targetId = 2
        fileMemoDatabase.deleteMemo(targetId)
        val res = fileMemoDatabase.readMemo()

        assertAll(
            { assertEquals(initialSize - 1, res.size) },         // 크기가 1 감소했는지 확인
            { assertFalse(res.any { it.id == targetId }) },     // 삭제한 ID를 가진 메모가 없는지 확인
            { assertTrue(res.none { it == memos[1] }) }             // 삭제한 메모가 리스트에 없는지 확인
        )
    }
    @Test
    fun modifyMemo(){
        fileMemoDatabase = FileMemoDatabase.getInstance()
        val initialSize  = fileMemoDatabase.readMemo().size
        val isModified= fileMemoDatabase.modifyMemo(Memo(3,"E",Category.TECH))
        val res = fileMemoDatabase.readMemo()
        val modified = res.find { it.id == 3 }

        assertAll(
            { assertTrue(isModified) },                    // 수정 성공
            { assertEquals(initialSize, res.size) },    // 크기는 변하지 않음
            { assertNotNull(modified)},               // 수정된 메모 존재
            { assertEquals("E", modified?.content) },  // 내용 수정됨
            { assertEquals(3, modified?.id) },         // id는 유지

            // 다른 메모들은 변경되지 않았는지 확인
            { assertEquals("A", res.find { it.id == 1 }?.content) },
            { assertEquals("B", res.find { it.id == 2 }?.content) }
        )

    }

}