    package com.thomas.airline.payment.service;

    import com.thomas.airline.booking.entity.Booking;
    import com.thomas.airline.booking.repository.BookingRepository;
    import com.thomas.airline.common.enums.PaymentStatus;
    import com.thomas.airline.exception.BookingNotFoundException;
    import com.thomas.airline.exception.PaymentAlreadyExistsException;
    import com.thomas.airline.exception.PaymentNotFoundException;
    import com.thomas.airline.payment.dto.PaymentRequestDto;
    import com.thomas.airline.payment.dto.PaymentResponseDto;
    import com.thomas.airline.payment.entity.Payment;
    import com.thomas.airline.payment.mapper.PaymentMapper;
    import com.thomas.airline.payment.repository.PaymentRepository;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    @Service
    public class PaymentService {
        private final PaymentRepository paymentRepository;
        private final PaymentMapper paymentMapper;
        private final BookingRepository bookingRepository;

        public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, BookingRepository bookingRepository) {
            this.paymentRepository = paymentRepository;
            this.paymentMapper = paymentMapper;
            this.bookingRepository = bookingRepository;
        }
        public String generateUniqueTransactionId(){
            String transactionId;
            do{
                transactionId= "TXN"+UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,15)
                        .toUpperCase();
            }while (paymentRepository.existsByTransactionId(transactionId));
            return transactionId;
        }
        public PaymentResponseDto createPayment(PaymentRequestDto requestDto){
            Booking booking=bookingRepository.findById(requestDto.getBookingId()).orElseThrow(()->new BookingNotFoundException("Booking is not available."));
            if(paymentRepository.existsByBookingId(booking.getId())){
                throw new PaymentAlreadyExistsException("Payment is already done.");
            }
            Payment payment=paymentMapper.requestDtoToPayment(requestDto);
            payment.setBooking(booking);
            payment.setTransactionId(generateUniqueTransactionId());
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setPaymentTime(LocalDateTime.now());
            Payment savedPayment=paymentRepository.save(payment);
            PaymentResponseDto responseDto=paymentMapper.paymentToResponseDto(savedPayment);
            return responseDto;
        }
        public List<PaymentResponseDto> getAllPayments(){
            List<Payment> payments=paymentRepository.findAll();
            List<PaymentResponseDto> responseDtos=new ArrayList<>();
            for(Payment payment: payments){
                PaymentResponseDto responseDto=paymentMapper.paymentToResponseDto(payment);
                responseDtos.add(responseDto);
            }
            return  responseDtos;
        }
        public PaymentResponseDto getPaymentById(Long id){
            Payment payment=paymentRepository.findById(id).orElseThrow(()-> new PaymentNotFoundException("Payment is not done."));
            PaymentResponseDto responseDto=paymentMapper.paymentToResponseDto(payment);
            return responseDto;
        }
        public PaymentResponseDto updatePayment(Long id, PaymentRequestDto requestDto){
            Payment payment=paymentRepository.findById(id).orElseThrow(()-> new PaymentNotFoundException("Payment is not done."));
            Booking booking=bookingRepository.findById(requestDto.getBookingId()).orElseThrow(()-> new BookingNotFoundException("Booking is not available."));
            Optional<Payment> existsBooking=paymentRepository.findByBookingId(requestDto.getBookingId());
            if(existsBooking.isPresent()&& !existsBooking.get().getId().equals(id)){
                throw new PaymentAlreadyExistsException("A payment already exists for this booking.");
            }
            payment.setAmount(requestDto.getAmount());
            payment.setPaymentMethod(requestDto.getPaymentMethod());
            payment.setBooking(booking);
            Payment savedPayment=paymentRepository.save(payment);
            PaymentResponseDto responseDto=paymentMapper.paymentToResponseDto(savedPayment);
            return responseDto;
        }
        public void  deletePayment(Long id){
            Payment payment=paymentRepository.findById(id).orElseThrow(()-> new  PaymentNotFoundException("Payment is not available."));
            paymentRepository.delete(payment);
        }
    }
