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

	.line 1
	new frame_0					; Frame 0 started
	dup
	invokespecial frame_0/<init>()V
	dup
	astore_1
	dup
	iconst_2
	putfield frame_0/x I
	dup
	iconst_3
	putfield frame_0/y I
	pop						; Frame 0 finished
	new frame_1					; Frame 1 started
	dup
	invokespecial frame_1/<init>()V
	dup
	aload_1
	putfield frame_1/SL Lframe_0;
	dup
	astore_1
	dup
	aload_1
	checkcast frame_1
	getfield frame_1/SL Lframe_0;
	getfield frame_0/x I
	aload_1
	checkcast frame_1
	getfield frame_1/SL Lframe_0;
	getfield frame_0/y I
	iadd
	putfield frame_1/k I
	pop						; Frame 1 finished
	aload_1
	checkcast frame_1
	getfield frame_1/SL Lframe_0;
	getfield frame_0/x I
	aload_1
	checkcast frame_1
	getfield frame_1/SL Lframe_0;
	getfield frame_0/y I
	iadd
	aload_1
	checkcast frame_1
	getfield frame_1/k I
	iadd
	aload_1					; Next 3ll: Get parent frame [0] and store it into local var 1
	checkcast frame_1				; Current frame stored in 1: 1
	getfield frame_1/SL Lframe_0;
	astore_1

	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return

.end method