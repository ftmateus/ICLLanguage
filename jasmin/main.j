.source main.j
.class public main
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V

	.limit locals 2
	.limit stack 256

	getstatic java/lang/System/out Ljava/io/PrintStream;

	sipush 15

	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return

.end method