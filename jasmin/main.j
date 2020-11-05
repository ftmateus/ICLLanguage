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

	new frame_0					; Frame 0 started
	dup
	invokespecial frame_0/<init>()V
	dup
	astore 1
	dup
	sipush 1
	putfield frame_0/x I
	pop						; Frame 0 finished
	aload 1
	checkcast frame_0
	getfield frame_0/x I

	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return

.end method