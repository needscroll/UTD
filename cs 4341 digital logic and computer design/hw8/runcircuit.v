`timescale 1ns/10ps

module testmux;
parameter width = 1;
reg [width-1:0] a, b, c;
wire [width-1:0] out;

mux2to1 mymux(a, b, c, out);

initial
begin
	$dumpfile("circuit.vcd");
	$dumpvars(0, testmux);
	$display("this program displays the citcuit Y = ~A & ~B | ~A & B & ~C | ~(A | ~C);");
	a = 1;
	b = 1;
	c = 1;
	#1
	a = 0;
	b = 0;
	c = 0;
	#1
	a = 1;
	#1
	$finish;
end

endmodule