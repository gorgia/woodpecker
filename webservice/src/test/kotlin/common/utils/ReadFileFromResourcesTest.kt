package common.utils

import org.junit.Assert
import org.junit.Test
import webint.woodpecker.common.utils.ReadFileFromResources

class  ReadFileFromResourcesTest{

    private val filepath = "testfile.txt"

    @Test
    fun readFile(){
        val list = ReadFileFromResources.readToList(filepath)
        Assert.assertNotNull(list)
    }
}