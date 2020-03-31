var x = 0

while (x < 5) {
	var y = 0
	x += 1
	while (y < 5) {
		y += 1
		val computation: String = (x * y).toString

		if(computation.contains("5") || computation.contains("6"))
			println(s"$x times $y is ${x * y}")
	}
}
