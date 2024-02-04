package dsl
import kotlin.collections.List
class MyJson {

    private val _list = MyList()
    fun list(callback: MyList.() -> Unit) {
        _list.callback()
    }

    override fun toString(): String {
        return "[\n$_list\n]"
    }
}

open class MyList : OneData() {

    fun item(callback: MyListItem.() -> Unit) {
        listData.add(MyListItem().apply(callback))
    }

    override fun toString(): String {
        return listData.joinToString(",\n")
    }
}

class MyListItem {
    private val _namePerson = NamePerson()
    private val _phonesPerson = PhonesPerson()
    private val _emailsPerson = EmailsPerson()

    fun name(callback: NamePerson.() -> Unit) {
        _namePerson.callback()
    }

    fun phones(callback: PhonesPerson.() -> Unit) {
        _phonesPerson.callback()
    }

    fun emails(callback: EmailsPerson.() -> Unit) {
        _emailsPerson.callback()
    }

    override fun toString(): String {
        return "{$_namePerson, $_phonesPerson, $_emailsPerson}"
    }
}

class NamePerson {
    private var name: String? = null

    fun addName(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "\"name\": \"$name\""
    }
}

class EmailsPerson {
    private var listEmail: List<String> = listOf()
    fun addData(listEmails: List<String>) {
        this.listEmail = listEmails
    }

    override fun toString(): String {
        return "\"emails\": [\"${listEmail.joinToString("\",\"")}\"]"
    }
}

class PhonesPerson {
    private var listPhone: List<String> = listOf()

    fun addData(listPhone: List<String>) {
        this.listPhone = listPhone
    }

    override fun toString(): String {
        return "\"phones\": [\"${listPhone.joinToString("\",\"")}\"]"
    }
}

open class OneData {
    protected val listData = mutableListOf<Any>()
}

fun json(callback: MyJson.() -> Unit): MyJson {
    val builder = MyJson()
    builder.callback()
    return builder
}
