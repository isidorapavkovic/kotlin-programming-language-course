package exercise9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {

    return employeesCSVLines
        .drop(1)
        .map { employee ->
            val data = employee.trim().split(",")
            val skills = data[4].trim().split("|").map { it.trim() }

            Employee(
                EmployeeID(data[0].trim()),
                data[1].trim(),
                Department(data[2].trim()),
                data[3].trim().toInt(),
                skills
            )
        }
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeePortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("exercise9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi : EmployeeApi = newEmployeeApi(employees)
}
