package lazygom.webslowcancel;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class WSCClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (name.equals("aok")) {
			System.out.println("aok patch.");
			return patchClassASM(name, basicClass, true);
		}
		if (name.equals("net.minecraft.block.BlockWeb")) {
			System.out.println("BlockWeb patch.");
			return patchClassASM(name, basicClass, false);
		}
		return basicClass;
	}
	
	public byte[] patchClassASM(String name, byte[] bytes, boolean obfuscated) {
		// func_149670_a : onEntityCollidedWithBlock
		String targetMethodName = "onEntityCollidedWithBlock";
		String targetMethodDesc = "(Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)V";
		if (obfuscated) {
			targetMethodName = "a";
			targetMethodDesc = "(Lahb;IIILsa;)V";
		}
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode methodNode = methods.next();
			System.out.println(methodNode.name + " " + methodNode.desc);
			if (methodNode.name.equals(targetMethodName) && methodNode.desc.equals(targetMethodDesc)) {
				Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
				while (iterator.hasNext()) {
					AbstractInsnNode node = iterator.next();
					if (node instanceof MethodInsnNode) {
						iterator.remove();
						break;
					}
				}
				break;
			}
		}
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(classWriter);
		return classWriter.toByteArray();
	}

}
