package jpabook.jpkshop.controller;

import jpabook.jpkshop.domain.Address;
import jpabook.jpkshop.domain.Member;
import jpabook.jpkshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new") // value = 생략가능
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm"; // get으로 members/new가 왔을때 createMemberForm을 내려보냄
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // @Valid를 통해서  MemberForm의    @NotEmpty가 체크되고
        // 결과는  BindingResult로 전달됨

        // MemberForm대신 Member를 binding받아도 되지 않나? no
        // => 아주 단순하면 그렇게 해도 되는데, 아니라면 member의 내용이 받으려는 것과 완전 일치하는것도 아니고
        // => validation 어노테이션을 member에 넣고 그러다보면 지저분 해짐.
        // 이쪽은 sprint mvc에 나온다는듯.

        if (result.hasErrors()) {
            return "members/createMemberForm"; // createMemberForm에 BindingResult에 대한 처리가 있음
        }
        Address address = new Address(form.getCity(), form.getStreet(),form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    //추가
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
        
        // 여기서도 단순해서 List<Member>를 그대로 화면에 뿌렸는데. 
        // 실무에서는 DTO를 이용해서 필요한 data만 뽑아서 출력하는거 추천
    }
}
