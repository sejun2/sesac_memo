package util

import io.mockk.every
import io.mockk.spyk
import model.Memo
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class FileMemoDatabaseTest {

    private lateinit var fileMemoDatabase: FileMemoDatabase
    private lateinit var fileMemoDatabaseForFail: FileMemoDatabase
    private lateinit var memos: List<Memo>
    private lateinit var file: File
    private lateinit var mockFile: File

    private val MEMO_FILE_NAME = "memo_test_file.txt"

    @BeforeTest
    fun beforeTest() {
        memos = listOf(
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
        mockFile = spyk(File("fail_memo_test_file.txt"))
        every { mockFile.exists() } returns false
        file = File(MEMO_FILE_NAME)
        fileMemoDatabase = FileMemoDatabase.getInstance(file)
        fileMemoDatabaseForFail = FileMemoDatabase.getInstance(mockFile)
    }

    @AfterTest
    fun afterTest(){
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
}