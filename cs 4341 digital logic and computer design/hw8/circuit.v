module mux2to1(A, B, C, Y);
parameter width = 1;
input [width-1:0] A, B, C;
output [width-1:0] Y;

assign Y = ~A & ~B | ~A & B & ~C | ~(A | ~C);

endmodule