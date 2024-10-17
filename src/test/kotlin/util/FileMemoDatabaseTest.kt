package util

import io.mockk.InternalPlatformDsl.toArray
import io.mockk.every
import io.mockk.mockk
import model.Memo
import org.jetbrains.annotations.TestOnly
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class FileMemoDatabaseTest {

    private lateinit var fileMemoDatabase: FileMemoDatabase
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
        mockFile = mockk<File>()
        every { mockFile.name } returns "testfile"
        file = File(MEMO_FILE_NAME)
        fileMemoDatabase = FileMemoDatabase.getInstance(file)
    }

    @AfterTest
    fun afterTest(){
        // remove test memo file after test
        file.delete()
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
        assertEquals(res, true)
    }

    @Test
    fun `when write memo failed, then return false`() {
        fileMemoDatabase = FileMemoDatabase.getInstance(mockFile)
        val res = fileMemoDatabase.writeMemo(memos)

        assertEquals(res, false)
    }

    @Test
    fun readMemo() {
        fileMemoDatabase = FileMemoDatabase.getInstance()
        val res = fileMemoDatabase.readMemo()

        assertArrayEquals(memos.toTypedArray(), res.toTypedArray())
    }
}