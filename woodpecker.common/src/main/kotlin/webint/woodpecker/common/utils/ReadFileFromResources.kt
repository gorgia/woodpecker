package webint.woodpecker.common.utils

object ReadFileFromResources {

    fun read(filepath:String): String{
        return ReadFileFromResources::class.java.classLoader.getResource(filepath).readText()
    }

    fun readToList(filepath:String): List<String>{
        val lineList = ArrayList<String>()
        val file = ReadFileFromResources::class.java.classLoader.getResource(filepath)
        file.openStream().bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }
        return lineList
    }
}