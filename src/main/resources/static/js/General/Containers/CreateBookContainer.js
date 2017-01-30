var CreateBookContainer = React.createClass({
  getInitialState: function() {
    return {
      author : '',
      title: '',
      isbn: '',
      publishedAt: new Date().getTime(),
      quantity: '',
    };
  },

  onHandleChangeAuthor : function(event){
    this.setState({author: event.target.value});
  },

  onHandleChangeTitle : function(event){
    this.setState({title: event.target.value});
  },

  onHandleChangeIsbn : function(event){
    this.setState({isbn: event.target.value});
  },

  onHandleChangePublishedAt : function(event){
    this.setState({publishedAt : event.target.value});
  },

  onHandleChangeQuantity : function(event){
    this.setState({quantity: event.target.value});
  },
  onHandleSubmit: function(event){
    var self = this;
    event.preventDefault();
    axios.post('/api/books', this.state)
    .then(function(response){
      console.log(response);
      self.context.router.push('/');
    })
    .catch(function(err){
      console.log('CreateBookContainer.onHandleSubmit - ',err);
    });

  },

  onHandleCancel:function(){
    this.context.router.push('/');
  },

  render: function() {
    return (
      <div>
      <AdminProductComponent
          submitButtonName='Create'

          author={this.state.author}
          title={this.state.title}
          isbn={this.state.isbn}
          publishedAt={this.state.publishedAt}
          quantity={this.state.quantity}

          onHandleChangeTitle={this.onHandleChangeTitle}
          onHandleChangeAuthor={this.onHandleChangeAuthor}
          onHandleChangeIsbn={this.onHandleChangeIsbn}
          onHandleChangePublishedAt={this.onHandleChangePublishedAt}
          onHandleChangeQuantity={this.onHandleChangeQuantity}

          onHandleUpdate={this.onHandleSubmit}
          onHandleCancel={this.onHandleCancel}

        />
      </div>
    );
  }
});


CreateBookContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.CreateBookContainer = CreateBookContainer;
