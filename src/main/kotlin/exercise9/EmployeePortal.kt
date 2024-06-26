package exercise9

class EmployeePortal(private val employees: List<Employee>): EmployeeApi {
    override fun findHighestPaidEmployee(): Employee? {
        return employees.maxByOrNull { it.salary }
    }

    override fun getEmployeesByDepartment(department: Department): List<Employee> {
        return employees.filter { it.department == department }
    }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> {
        return employees.filter { it.salary in salaryRange }
    }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        val employees = getEmployeesByDepartment(department)
        return employees.sumOf { it.salary }.toDouble().div(employees.size)
    }

    override fun findMostCommonSkill(): String {
        return employees
            .flatMap { it.skills }
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            .key
    }
}